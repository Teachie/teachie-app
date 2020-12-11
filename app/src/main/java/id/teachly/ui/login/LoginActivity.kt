package id.teachly.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import id.teachly.databinding.ActivityLoginBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.ui.base.MainActivity
import id.teachly.ui.register.RegisterActivity
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.showError
import id.teachly.utils.Helpers.tag

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
                            if (Helpers.errorLoginMessage.values.indexOf(message) < 2)
                                showErrorEmail(message)
                            else showErrorPassword(message)
                        }
                    }
                } else {
                    validateField()
                    Helpers.hideLoadingDialog()
                }
            }
            tvNoAccount.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
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
        Log.d(
            this.tag(),
            "Login Success: user= ${Auth.getCurrentUser()}"
        )
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

}