package id.teachly.ui.register

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.teachly.data.Users
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.repo.remote.firebase.storage.StorageUser
import id.teachly.utils.Helpers.loadDrawable
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    fun createAccount(email: String, pass: String, isSuccess: (Boolean, String) -> Unit) {
        Auth.signUpWithEmail(email, pass) { b, message -> isSuccess(b, message) }
    }

    fun loadImage(img: String, context: Context, onSuccess: (Drawable) -> Unit) =
        viewModelScope.launch {
            onSuccess(img.loadDrawable(context))
        }

    fun uploadPhoto(img: Uri, isSuccess: (Boolean) -> Unit) {
        StorageUser.uploadPhoto(img) { b, url ->
            FirestoreUser.updatePhotoUser(url) {
                if (it) isSuccess(true)
                else isSuccess(false)
            }
        }
    }

    fun createDataUser(users: Users, isSuccess: (Boolean) -> Unit) {
        FirestoreUser.createNewUser(users) { isSuccess(it) }
    }

}