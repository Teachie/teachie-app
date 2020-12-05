package id.teachly.ui.base.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.databinding.ItemTimelineBinding
import id.teachly.utils.Helpers

class HomeAdapter(
    private val context: Context,
    private val size: Int
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
        binding.apply {
            ivAva.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            ivTopic.load(Helpers.dummyTopic) {
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