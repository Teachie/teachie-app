package id.teachly.ui.detailsection

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.data.StoryContent
import id.teachly.databinding.ItemContentImgBinding
import id.teachly.databinding.ItemContentTextBinding

class ContentAdapter(
    private val context: Context,
    private val dataContent: List<StoryContent>
) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var contentTextBinding: ItemContentTextBinding
    private lateinit var contentImageBinding: ItemContentImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = when (viewType) {
            0 -> {
                contentTextBinding = ItemContentTextBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_content_text, parent, false)
                )
                contentTextBinding.root
            }
            else -> {
                contentImageBinding = ItemContentImgBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_content_img, parent, false)
                )
                contentImageBinding.root
            }
        }
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val content = dataContent[position]
        when (getItemViewType(position)) {
            0 -> {
                contentTextBinding.tvContent.text =
                    HtmlCompat.fromHtml(content.data, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }
            1 -> {
                val split = content.data.split("amp;")
                var img = ""
                split.forEach { img += it }

                Log.d("ContentAdapter", "load: img = $img")
                contentImageBinding.ivContent.load(img) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(8f))
                }
            }
        }
    }

    override fun getItemCount(): Int = dataContent.size

    override fun getItemViewType(position: Int): Int {
        return dataContent[position].type
    }
}