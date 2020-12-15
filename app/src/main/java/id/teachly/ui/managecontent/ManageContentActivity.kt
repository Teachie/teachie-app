package id.teachly.ui.managecontent

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import id.teachly.databinding.ActionbarTopicBinding
import id.teachly.databinding.ActivityManageContentBinding
import id.teachly.ui.managecontent.fragment.SectionsPagerAdapter
import id.teachly.utils.Helpers.hideView

class ManageContentActivity : AppCompatActivity() {

    private val titles = listOf("Bagian", "Ruang")
    private lateinit var binding: ActivityManageContentBinding
    private lateinit var actionbarBinding: ActionbarTopicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageContentBinding.inflate(layoutInflater)
        actionbarBinding = binding.actionBarTopic
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionbarBinding.apply {
            tvTitle.text = "Kelola Konten"
            button.text = "Buat Ruang"
        }

        if (isSpaceAvailable()) actionbarBinding.button.hideView()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, titles)

        binding.apply {
            viewPager.adapter = sectionsPagerAdapter

            tabs.setupWithViewPager(viewPager)

            if (!isSpaceAvailable()) tabs.hideView()


            fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

    }

    private fun isSpaceAvailable() = titles.size == 2

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}