package id.teachly.ui.detailsection

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import id.teachly.data.Story
import id.teachly.data.Users
import id.teachly.databinding.ActionbarSectionBinding
import id.teachly.databinding.ActivityDetailSectionBinding
import id.teachly.databinding.LayoutScrollingSectionBinding
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.detaildiscussion.DetailDiscussionActivity
import id.teachly.ui.search.fragment.UserAdapter
import id.teachly.utils.Const
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.decodeContent
import id.teachly.utils.Helpers.formatDate
import id.teachly.utils.Helpers.hideView


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
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        val story = intent.getParcelableExtra<Story>(EXTRA_CONTENT)

        if (story?.spaceId == null) actionBarBinding.contentMain.hideView()

        actionBarBinding.imageView3.load(Helpers.dummyTopic) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        contentBinding.apply {
            FirestoreUser.getUserById(story?.writerId ?: "") { users ->
                ivAva.load(users.img) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }

                tvName.text = users.fullName

                val writer = Users(
                    fullName = "Ditulis oleh",
                    username = users.fullName,
                    bio = users.bio,
                    img = users.img
                )

                rvWriter.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = UserAdapter(this@DetailSectionActivity, listOf(writer))
                }
            }

            tvDate.text = story?.dateCreated?.formatDate(Const.DateFormat.LONG)
            tvTitle.text = story?.title
            cbLike.text = story?.love.toString()
            cbComment.text = story?.discuss.toString()

            story?.categories?.forEach {
                chipGroup.addView(Chip(chipGroup.context).apply { text = it })
            }

            rvContent.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = ContentAdapter(
                    this@DetailSectionActivity,
                    story?.content?.decodeContent() ?: listOf()
                )
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

    companion object {
        const val EXTRA_CONTENT = "extra_content"
    }
}