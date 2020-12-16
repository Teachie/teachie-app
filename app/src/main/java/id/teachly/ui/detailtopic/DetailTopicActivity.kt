package id.teachly.ui.detailtopic

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearSnapHelper
import id.teachly.databinding.ActionbarTopicBinding
import id.teachly.databinding.ActivityDetailTopicBinding
import id.teachly.ui.base.fragment.home.HomeAdapter

class DetailTopicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTopicBinding
    private lateinit var actionbarBinding: ActionbarTopicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopicBinding.inflate(layoutInflater)
        actionbarBinding = binding.layoutActionBarTopic
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        actionbarBinding.tvTitle.text = intent.extras?.getString(DETAIL_TOPIC_EXTRA)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {

            LinearSnapHelper().attachToRecyclerView(rvSpace)
            LinearSnapHelper().attachToRecyclerView(rvTrendingSection)

            rvSpace.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = SpaceAdapter(this@DetailTopicActivity, 10, 2)
            }

            rvTrendingSection.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = TrendingAdapter(this@DetailTopicActivity, 10)
            }

            rvSection.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = HomeAdapter(this@DetailTopicActivity, 10)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DETAIL_TOPIC_EXTRA = "detail_topic_extra"
    }
}