package id.teachly.ui.detailtopic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearSnapHelper
import id.teachly.databinding.ActivityDetailTopicBinding
import id.teachly.ui.base.fragment.home.HomeAdapter

class DetailTopicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTopicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val snapHelper = LinearSnapHelper()

            snapHelper.apply {
                attachToRecyclerView(rvSpace)
                attachToRecyclerView(rvTrendingSection)
            }

            rvSpace.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = SpaceAdapter(this@DetailTopicActivity, 10)
            }

            rvTrendingSection.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = HomeAdapter(this@DetailTopicActivity, 10)
            }

            rvSection.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = HomeAdapter(this@DetailTopicActivity, 10)
            }


        }


    }
}