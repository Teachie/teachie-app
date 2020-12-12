package id.teachly.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.teachly.R
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.base.MainActivity
import id.teachly.ui.welcome.WelcomeActivity
import id.teachly.utils.Helpers

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Helpers.startDelay(1) {
            if (Auth.getCurrentUser() != null) {
                FirestoreUser.getUserById(Auth.getUserId() ?: "") {
                    if (it.username != null) navigateToMain()
                    else Auth.logout(); navigateToWelcome()
                    finish()
                }
            } else navigateToWelcome(); finish()
        }
    }

    private fun navigateToWelcome() = startActivity(Intent(this, WelcomeActivity::class.java))

    private fun navigateToMain() = startActivity(Intent(this, MainActivity::class.java))
}