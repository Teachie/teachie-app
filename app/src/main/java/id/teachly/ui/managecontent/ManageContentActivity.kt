package id.teachly.ui.managecontent

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import id.teachly.databinding.ActionbarTopicBinding
import id.teachly.databinding.ActivityManageContentBinding
import id.teachly.ui.managecontent.fragment.ManageContentPagerAdapter
import id.teachly.ui.publishsection.addspace.AddSpaceActivity
import id.teachly.utils.Helpers.getPageSelected
import id.teachly.utils.Helpers.hideView

class ManageContentActivity : AppCompatActivity() {


    private lateinit var binding: ActivityManageContentBinding
    private lateinit var actionbarBinding: ActionbarTopicBinding
    private val model: ManageContentViewModel by viewModels()

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

        model.loadSpace()
        model.space.observe(this, {
            val titles = mutableListOf("Cerita")
            if (it.isNotEmpty()) titles.add("Ruang")
            if (isSpaceAvailable(titles)) actionbarBinding.button.hideView()
            populateDataAdapter(titles)
        })


    }

    private fun populateDataAdapter(titles: MutableList<String>) {
        val sectionsPagerAdapter = ManageContentPagerAdapter(this, supportFragmentManager, titles)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            hideFab()

            viewPager.addOnPageChangeListener(getPageSelected {
                if (it == 0) hideFab() else showFab()
            })

            tabs.setupWithViewPager(viewPager)

            if (!isSpaceAvailable(titles)) tabs.hideView()

            fab.setOnClickListener {
                startActivity(Intent(this@ManageContentActivity, AddSpaceActivity::class.java))
            }
        }
    }

    private fun isSpaceAvailable(titles: MutableList<String>) = titles.size == 2

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun hideFab() = binding.fab.hide()
    private fun showFab() = binding.fab.show()
}