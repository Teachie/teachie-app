package id.teachly.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import id.teachly.R
import id.teachly.databinding.ActivityLoginBinding
import id.teachly.databinding.LayoutForgetPasswordBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.base.MainActivity
import id.teachly.ui.register.RegisterActivity
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.showError

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        binding.apply {
            edtEmail.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            edtPassword.addTextChangedListener(Helpers.getTextWatcher { validateError() })

            btnLogin.setOnClickListener {
                Helpers.showLoadingDialog(this@LoginActivity)
                if (isNotEmpty()) {
                    model.loginWithEmail(
                        edtEmail.text.toString(),
                        edtPassword.text.toString()
                    ) { isSuccess, message ->
                        if (isSuccess) moveToMain()
                        else {
                            Helpers.hideLoadingDialog()
                            if (Helpers.errorLoginMessage.values.indexOf(message) < 3)
                                showErrorEmail(message)
                            else showErrorPassword(message)
                        }
                    }
                } else {
                    validateField()
                    Helpers.hideLoadingDialog()
                }
            }

            tvForgetPassword.setOnClickListener { showForgetPasswordDialog() }

        }
    }

    private fun showForgetPasswordDialog() {
        Helpers.createBottomSheetDialog(this, R.layout.layout_forget_password) { view, dialog ->
            val passwordBinding = LayoutForgetPasswordBinding.bind(view)
            passwordBinding.apply {
                edtEmail.addTextChangedListener(Helpers.getTextWatcher {
                    Helpers.validateError(tilEmail)
                })
                btnClose.setOnClickListener { dialog.dismiss() }
                btnSave.setOnClickListener {
                    if (edtEmail.text.toString().isEmpty()) {
                        sendResetPasswordEmail(edtEmail.text.toString()) { isSuccess, message ->
                            if (isSuccess) {
                                dialog.dismiss()
                                Helpers.showToast(
                                    this@LoginActivity,
                                    "Pesan pengaturan ulang berhasil dikirim"
                                )
                            } else tilEmail.showError(message)
                        }

                    } else tilEmail.showError(Helpers.errorEmptyLoginMessage[0])
                }
            }
            dialog.show()
        }
    }

    private fun sendResetPasswordEmail(
        email: String,
        onResetSend: (isSuccess: Boolean, message: String) -> Unit
    ) {
        Auth.forgetPassword(email) { b, message -> onResetSend(b, message) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        if (item.itemId == R.id.action_register)
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_login, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun isNotEmpty(): Boolean {
        return !binding.edtEmail.text.isNullOrEmpty() && !binding.edtPassword.text.isNullOrEmpty()
    }

    private fun validateField() {
        if (binding.edtEmail.text.isNullOrEmpty()) showErrorEmail(Helpers.errorEmptyLoginMessage[0])
        if (binding.edtPassword.text.isNullOrEmpty()) showErrorPassword(Helpers.errorEmptyLoginMessage[1])
    }

    private fun showErrorPassword(message: String) {
        binding.tilPassword.showError(message)
    }

    private fun showErrorEmail(message: String) {
        binding.tilEmail.showError(message)
    }

    private fun validateError() {
        binding.apply {
            Helpers.validateError(tilPassword, tilEmail)
        }
    }

    private fun moveToMain() {
        FirestoreUser.getUserById(Auth.getUserId() ?: "") {
            if (it.username == null)
                startActivity(Intent(this, RegisterActivity::class.java).apply {
                    putExtra(RegisterActivity.REGISTER_EXTRA, 1)
                })
            else startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}