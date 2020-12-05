package id.teachly.ui.base.fragment.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.R
import id.teachly.databinding.ItemTopicBinding
import id.teachly.utils.Helpers

class ExploreAdapter(
    private val context: Context,
    private val size: Int
) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {
    class ExploreViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemTopicBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        binding = ItemTopicBinding.bind(view)
        return ExploreViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        binding.apply {
            ivTopic.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemCount(): Int = size

}