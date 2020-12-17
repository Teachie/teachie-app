package id.teachly.ui.base.fragment.discussion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.databinding.ItemDiscussionBinding
import id.teachly.utils.Helpers

class DiscussAdapter(
    private val context: Context,
    private val size: Int
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
        binding.apply {
            ivAva.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            ivBanner.load(Helpers.dummyBg) {
                crossfade(true)
                transformations(RoundedCornersTransformation(10f))
            }
        }
    }

    override fun getItemCount(): Int = size

}