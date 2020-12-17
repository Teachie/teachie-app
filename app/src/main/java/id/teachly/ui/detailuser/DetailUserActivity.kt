package id.teachly.ui.detailuser

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.databinding.ActivityDetailUserBinding
import id.teachly.databinding.LayoutInfoUserBinding
import id.teachly.ui.detailuser.fragment.SectionsPagerAdapter
import id.teachly.utils.Helpers

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var layoutUserBinding: LayoutInfoUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        layoutUserBinding = binding.layoutUser
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        binding.apply {
            val sectionsPagerAdapter =
                SectionsPagerAdapter(this@DetailUserActivity, supportFragmentManager)
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

        layoutUserBinding.ivAva.load(Helpers.dummyAva) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}