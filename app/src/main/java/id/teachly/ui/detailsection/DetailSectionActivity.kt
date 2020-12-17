package id.teachly.ui.detailsection

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.databinding.ActionbarSectionBinding
import id.teachly.databinding.ActivityDetailSectionBinding
import id.teachly.databinding.LayoutScrollingSectionBinding
import id.teachly.ui.detaildiscussion.DetailDiscussionActivity
import id.teachly.ui.search.fragment.UserAdapter
import id.teachly.utils.Helpers

class DetailSectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSectionBinding
    private lateinit var actionBarBinding: ActionbarSectionBinding
    private lateinit var contentBinding: LayoutScrollingSectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSectionBinding.inflate(layoutInflater)
        actionBarBinding = binding.actionBarSection
        contentBinding = binding.layoutScrollingSection
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarBinding.imageView3.load(Helpers.dummyTopic) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        contentBinding.apply {
            ivAva.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            rvWriter.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = UserAdapter(this@DetailSectionActivity, 2)
            }
        }

        binding.fabDiscussion.setOnClickListener {
            startActivity(Intent(this, DetailDiscussionActivity::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}