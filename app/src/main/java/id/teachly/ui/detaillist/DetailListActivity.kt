package id.teachly.ui.detaillist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.teachly.data.DetailData
import id.teachly.databinding.ActivityDetailListBinding

class DetailListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.extras?.getParcelable(DETAIL_EXTRA) ?: DetailData()


    }

    companion object {
        const val DETAIL_EXTRA = "detail_extra"
    }
}