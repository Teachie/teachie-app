package id.teachly.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.teachly.databinding.ActivityWelcomeBinding
import id.teachly.ui.login.LoginActivity
import id.teachly.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnRegister.setOnClickListener {
                startActivity(
                    Intent(this@WelcomeActivity, RegisterActivity::class.java)
                )
            }
            tvLogin.setOnClickListener {
                startActivity(
                    Intent(this@WelcomeActivity, LoginActivity::class.java)
                )
            }
        }
    }
}