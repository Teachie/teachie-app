package id.teachly.ui.detailtopic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.databinding.ItemTrendingBinding
import id.teachly.ui.detailsection.DetailSectionActivity
import id.teachly.utils.Helpers

class TrendingAdapter(
    private val context: Context,
    private val size: Int
) : RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {

    class TrendingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemTrendingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_trending, parent, false)
        binding = ItemTrendingBinding.bind(view)
        return TrendingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        binding.apply {
            ivSection.load(Helpers.dummyBg) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }

            ivSpace.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            ivAva.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            contentSection.setOnClickListener {
                context.startActivity(Intent(context, DetailSectionActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int = size


}