package id.teachly.ui.base.fragment.discussion

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.data.Discussion
import id.teachly.databinding.ItemDiscussionBinding
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.detaildiscussion.DetailDiscussionActivity
import id.teachly.utils.Helpers.hideView

class DiscussAdapter(
    private val context: Context,
    private val listDiscussion: List<Discussion>
) : RecyclerView.Adapter<DiscussAdapter.DiscussViewHolder>() {

    class DiscussViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemDiscussionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_discussion, parent, false)
        binding = ItemDiscussionBinding.bind(view)
        return DiscussViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DiscussViewHolder, position: Int) {

        val discussion = listDiscussion[position]

        binding.apply {
            FirestoreUser.getUserById(discussion.writerId ?: "") {
                ivAva.load(it.img) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvFullName.text = it.fullName
                tvUsername.text = it.username
            }

            if (discussion.img != null) {
                ivBanner.load(discussion.img) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(10f))
                }
            } else ivBanner.hideView()

            tvTitle.text = discussion.title
            tvDesc.text = discussion.question

            discussion.category?.forEach {
                chipGroup.addView(
                    Chip(context).apply { text = it }
                )
            }

            cbDiscussion.text = discussion.responses.toString()

            contentMain.setOnClickListener {
                context.startActivity(
                    Intent(context, DetailDiscussionActivity::class.java)
                        .putExtra(
                            DetailDiscussionActivity.EXTRA_DISCUSSION_ID,
                            discussion.discussionId
                        )
                )
            }

        }
    }

    override fun getItemCount(): Int = listDiscussion.size

}