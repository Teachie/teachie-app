package id.teachly.ui.login

import androidx.lifecycle.ViewModel
import id.teachly.repo.remote.firebase.auth.Auth

class LoginViewModel : ViewModel() {

    fun loginWithEmail(email: String, pass: String, onSuccess: (Boolean, message: String) -> Unit) {
        Auth.loginWithEmail(
            email, pass
        ) { isSuccess, message ->
            if (isSuccess) onSuccess(true, "Login Success")
            else onSuccess(false, message)
        }
    }
}