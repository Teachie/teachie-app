package id.teachly.repo.remote.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.teachly.utils.Helpers

object Auth {
    private const val TAG = "Auth"
    private val instance = FirebaseAuth.getInstance()

    fun signUpWithEmail(
        email: String,
        pass: String,
        isSuccess: (Boolean, message: String) -> Unit
    ) {
        instance.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> isSuccess(true, "Sukses membuat akun baru")
                    false -> {
                        Log.d(TAG, "signUpWithEmail: failed = ${it.exception}")
                        isSuccess(false, Helpers.errorLoginMessage[it.exception?.message] ?: "")
                    }
                }
            }
    }

    fun loginWithEmail(
        email: String, pass: String,
        onUserCreated: (isSuccess: Boolean, message: String) -> Unit
    ) {
        instance.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) onUserCreated(true, "Login Success")
                else onUserCreated(
                    false,
                    Helpers.errorLoginMessage[it.exception?.message] ?: ""
                )
            }
    }

    fun logout() {
        instance.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return instance.currentUser
    }

    fun getUserId(): String? {
        return getCurrentUser()?.uid
    }
}