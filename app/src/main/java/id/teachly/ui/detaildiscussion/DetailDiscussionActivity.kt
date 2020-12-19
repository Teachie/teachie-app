package id.teachly.ui.detaildiscussion

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import id.teachly.data.Discussion
import id.teachly.data.Response
import id.teachly.data.Users
import id.teachly.databinding.ActivityDetailDiscussionBinding
import id.teachly.databinding.LayoutCommentBarBinding
import id.teachly.databinding.LayoutContentDiscussionBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreDiscussion
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.utils.Const
import id.teachly.utils.Helpers.formatDate
import id.teachly.utils.Helpers.hideView
import kotlinx.android.synthetic.main.activity_detail_discussion.*
import kotlinx.android.synthetic.main.layout_comment_bar.view.*

class DetailDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDiscussionBinding
    private lateinit var contentBinding: LayoutContentDiscussionBinding
    private lateinit var barBinding: LayoutCommentBarBinding
    private val model: DetailDiscussionViewModel by viewModels()
    private lateinit var currentUser: Users
    private var currentDiscussionId = ""
    private var currentTotalSection = 0

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

        val storyId = intent.getStringExtra(EXTRA_DISCUSSION)
        val discussionId = intent.getStringExtra(EXTRA_DISCUSSION_ID)

        if (storyId == null) {
            FirestoreDiscussion.getDiscussionByDiscussionId(discussionId ?: "") { b, list ->
                populateDataDiscussion(listOf(list))
            }
        } else {
            model.getDiscussion(storyId)
            model.discussion.observe(this, {
                populateDataDiscussion(it)
            })
        }


        model.response.observe(this, {
            populateDataResponse(it)
        })


        FirestoreUser.getUserById(Auth.getCurrentUser()?.uid ?: "") {
            currentUser = it
            barBinding.ivAvatar.load(it.img) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

        layoutChatBar.apply {
            this.fabSend.setOnClickListener {
                if (edtChat.text.isNotEmpty()) {
                    model.postNewResponse(
                        Response(
                            discussionId = currentDiscussionId,
                            writerId = Auth.getCurrentUser()?.uid,
                            comment = edtChat.text.toString()
                        ), currentDiscussionId, currentTotalSection
                    )
                    edtChat.text.clear()
                }
            }
        }

    }

    private fun populateDataResponse(it: List<Response>?) {
        contentBinding.apply {
            rvResponse.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = ResponseAdapter(this@DetailDiscussionActivity, it ?: listOf())
            }
        }
    }

    private fun populateDataDiscussion(discussion: List<Discussion>?) {

        val mDiscussion = discussion?.get(0)
        currentDiscussionId = mDiscussion?.discussionId ?: ""
        currentTotalSection = mDiscussion?.responses ?: 0
        model.getResponse(currentDiscussionId)

        contentBinding.apply {

            if (mDiscussion?.img == null) ivContent.hideView()

            FirestoreUser.getUserById(mDiscussion?.writerId ?: "") {
                ivAva.load(it.img) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvUsername.text = it.fullName
                tvTime.text = mDiscussion?.time?.formatDate(Const.DateFormat.LONG)
            }

            mDiscussion?.category?.forEach {
                chipGroup.addView(Chip(chipGroup.context).apply { text = it })
            }

            ivContent.load(mDiscussion?.img) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }

            tvTitle.text = mDiscussion?.title
            tvDesc.text = mDiscussion?.question
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_DISCUSSION = "extra_discussion"
        const val EXTRA_DISCUSSION_ID = "extra_discussion_id"
    }
}