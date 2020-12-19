package id.teachly.ui.base.fragment.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.data.Story
import id.teachly.databinding.ItemTimelineBinding
import id.teachly.repo.remote.firebase.firestore.FirestoreSpace
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.detailsection.DetailSectionActivity
import id.teachly.utils.Const
import id.teachly.utils.Helpers.decodeContent
import id.teachly.utils.Helpers.formatDate
import id.teachly.utils.Helpers.hideView

class HomeAdapter(
    private val context: Context,
    private val contentList: List<Story>
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemTimelineBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_timeline, parent, false)
        binding = ItemTimelineBinding.bind(view)
        return HomeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val content = contentList[position]
        binding.apply {
            FirestoreUser.getUserById(content.writerId ?: "") {
                ivAva.load(it.img) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvFullName.text = it.fullName
                tvUsername.text = "@${it.username}"
                tvTime.text = it.date?.formatDate(Const.DateFormat.SHORT)
            }

            if (content.spaceId == null) contentSpace.hideView()
            else {
                FirestoreSpace.getSpaceById(content.spaceId) { b, space ->
                    tvNameSpace.text = space.title
                    tvTotalStory.text = "${space.section} Cerita"
                    ivTopic.load(space.img) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                }
            }



            if (content.thumbnail != null) {
                ivBanner.load(content.thumbnail) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(10f))
                }
            } else ivBanner.hideView()

            tvTitle.text = content.title

            val data = content.content?.decodeContent()
            val dataContent = data?.filter { it.type != 1 }
            val stringBuild = buildString {
                dataContent?.forEach { append(it.data) }
            }

            tvContent.text =
                HtmlCompat.fromHtml(stringBuild, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            cbLike.text = content.love.toString()
            cbDiscuss.text = content.discuss.toString()

            contentSection.setOnClickListener {
                context.startActivity(Intent(context, DetailSectionActivity::class.java)
                    .apply { putExtra(DetailSectionActivity.EXTRA_CONTENT, content) })
            }
        }
    }

    override fun getItemCount(): Int = contentList.size

}