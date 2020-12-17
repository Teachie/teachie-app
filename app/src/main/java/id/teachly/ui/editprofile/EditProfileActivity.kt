package id.teachly.ui.editprofile

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import id.teachly.data.Users
import id.teachly.databinding.ActivityEditProfileBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.repo.remote.firebase.storage.StorageUser
import id.teachly.utils.Const
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.formatDate
import id.teachly.utils.Helpers.showError
import id.teachly.utils.Helpers.tag
import id.teachly.utils.Helpers.toTimeStamp
import java.util.regex.Pattern

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var imgUrl: Uri
    private var isPhotoChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Ubah Profil"
            setDisplayHomeAsUpEnabled(true)
        }

        FirestoreUser.getUserById(Auth.getUserId() ?: "") { users ->
            binding.apply {
                imgUrl = users.img?.toUri() ?: "".toUri()
                ivAva.load(imgUrl) { crossfade(true) }
                edtFullName.setText(users.fullName)
                edtUsername.setText(users.username)
                edtDate.setText(users.date?.formatDate(Const.DateFormat.SIMPLE))
                if (users.bio != null) edtBio.setText(users.bio)

                edtUsername.addTextChangedListener(Helpers.getTextWatcher { validateError() })
                edtFullName.addTextChangedListener(Helpers.getTextWatcher { validateError() })
                edtBio.addTextChangedListener(Helpers.getTextWatcher { validateError() })
                edtDate.addTextChangedListener(Helpers.getTextWatcher { validateError() })

                edtDate.setOnClickListener {
                    Helpers.showDatePicker(this@EditProfileActivity) { date ->
                        edtDate.setText(date)
                    }
                }

                ivPickPhoto.setOnClickListener {
                    ImagePicker.with(this@EditProfileActivity)
                        .cropSquare().start { resultCode, data ->
                            when (resultCode) {
                                Activity.RESULT_OK -> {
                                    imgUrl = data?.data ?: "".toUri()
                                    isPhotoChange = true
                                    ivAva.load(imgUrl) { crossfade(true) }
                                }
                                ImagePicker.RESULT_ERROR -> {
                                    Helpers.showToast(
                                        this@EditProfileActivity,
                                        ImagePicker.getError(data)
                                    )
                                }
                                else -> {
                                    Helpers.showToast(this@EditProfileActivity, "Batal")
                                }
                            }
                        }
                }

                btnSave.setOnClickListener {
                    if (isNotEmpty()) {
                        if (thereIsAChange(users)) {
                            Helpers.showLoadingDialog(
                                this@EditProfileActivity, "Sedang memperbaharui datamu"
                            )
                            if (isUsernameChanged(users)) {
                                if (isUsernameValid()) {
                                    isUsernameAvailable { available ->
                                        if (available) updateDataUser()
                                        else showErrorUsername(4)
                                    }
                                } else showErrorUsername(3)
                            } else updateDataUser()
                        } else finish()
                    } else validateField()
                }
            }
        }
    }

    private fun updateDataUser() {

        binding.apply {
            FirestoreUser.updateUser(
                Users(
                    username = edtUsername.text.toString(),
                    fullName = edtFullName.text.toString(),
                    bio = if (edtBio.text.toString().isNotEmpty()) edtBio.text.toString() else null,
                    date = edtDate.text.toString().toTimeStamp(Const.DateFormat.SIMPLE)
                )
            ) {
                if (isPhotoChange) updatePhoto { isUpdate ->
                    if (isUpdate) finish()
                    else Helpers.hideLoadingDialog()
                }
                else {
                    if (it) finish()
                    else Helpers.hideLoadingDialog()
                }
            }
        }
    }

    private fun updatePhoto(isSuccess: (Boolean) -> Unit) {
        StorageUser.uploadPhoto(imgUrl) { _, url ->
            FirestoreUser.updatePhotoUser(url) {
                if (it) isSuccess(true)
                else isSuccess(false)
            }
        }
    }

    private fun thereIsAChange(users: Users): Boolean {
        binding.apply {
            return (imgUrl.toString() != users.img || edtFullName.text.toString() != users.fullName
                    || isUsernameChanged(users) || edtBio.text.toString() != users.bio
                    || edtDate.text.toString() != users.date?.formatDate(Const.DateFormat.SIMPLE))
        }
    }

    private fun isUsernameChanged(users: Users): Boolean =
        binding.edtUsername.text.toString() != users.username


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun isNotEmpty(): Boolean {
        return !binding.edtFullName.text.isNullOrEmpty() &&
                !binding.edtUsername.text.isNullOrEmpty() &&
                !binding.edtDate.text.isNullOrEmpty()
    }

    private fun isUsernameValid(): Boolean {
        return binding.edtUsername.text.toString().split(" ").size == 1 && noSpecialChar()
    }

    private fun noSpecialChar(): Boolean {
        val pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        return !pattern.matcher(binding.edtUsername.text.toString()).find()
    }

    private fun isUsernameAvailable(isAvailable: (Boolean) -> Unit) {
        FirestoreUser.getUserByUsername(binding.edtUsername.text.toString()) {
            Log.d(this.tag(), "isUsernameAvailable: $it")
            isAvailable(it.username == null)
        }
    }

    private fun validateField() {
        binding.apply {
            if (edtFullName.text.isNullOrEmpty()) tilFullName.showError(Helpers.errorEmptyFillDataMessage[0])
            if (edtUsername.text.isNullOrEmpty()) tilUsername.showError(Helpers.errorEmptyFillDataMessage[1])
            if (edtDate.text.isNullOrEmpty()) tilDate.showError(Helpers.errorEmptyFillDataMessage[2])
        }
    }

    private fun validateError() {
        binding.apply {
            Helpers.validateError(tilFullName, tilUsername, tilDate, tilBio)
        }
    }

    private fun showErrorUsername(data: Int) {
        Helpers.hideLoadingDialog()
        binding.tilUsername.showError(Helpers.errorEmptyFillDataMessage[data])
    }
}