package id.teachly.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.teachly.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var data = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.extras?.getInt(REGISTER_EXTRA) ?: -1
    }

    fun getData(): Int = data

    companion object {
        const val REGISTER_EXTRA = "register_extra"
    }
}