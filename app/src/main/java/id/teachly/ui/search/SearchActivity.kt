package id.teachly.ui.search

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.teachly.databinding.ActivitySearchBinding
import id.teachly.ui.search.fragment.SectionsPagerAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        binding.apply {
            GlobalScope.launch {
                searchView.setQuery(intent.extras?.getString(SEARCH_EXTRA), false)
            }
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val SEARCH_EXTRA = "search_extra"
    }
}