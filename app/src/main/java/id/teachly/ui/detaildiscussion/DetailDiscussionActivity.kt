package id.teachly.ui.detaildiscussion

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.databinding.ActivityDetailDiscussionBinding
import id.teachly.databinding.LayoutCommentBarBinding
import id.teachly.databinding.LayoutContentDiscussionBinding
import id.teachly.utils.Helpers

class DetailDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDiscussionBinding
    private lateinit var contentBinding: LayoutContentDiscussionBinding
    private lateinit var barBinding: LayoutCommentBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussionBinding.inflate(layoutInflater)
        contentBinding = binding.layoutContentDiscussion
        barBinding = binding.layoutChatBar
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        contentBinding.apply {
            rvResponse.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = ResponseAdapter(this@DetailDiscussionActivity, 10)
            }

            ivAva.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            ivContent.load(Helpers.dummyBg) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
        }
        barBinding.ivAvatar.load(Helpers.dummyAva) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}