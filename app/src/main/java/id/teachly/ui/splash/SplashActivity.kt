package id.teachly.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.teachly.R
import id.teachly.ui.login.LoginActivity
import id.teachly.utils.Helpers

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Helpers.startDelay(2) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}