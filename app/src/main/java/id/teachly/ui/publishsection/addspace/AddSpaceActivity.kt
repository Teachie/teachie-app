package id.teachly.ui.publishsection.addspace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.teachly.databinding.ActivityAddSpaceBinding

class AddSpaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSpaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Buat Ruang"
            setDisplayHomeAsUpEnabled(true)
        }


    }
}