package id.teachly.ui.detailtopic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.databinding.ItemSpaceBinding
import id.teachly.utils.DummyData
import id.teachly.utils.Helpers

class SpaceAdapter(
    private val context: Context,
    private val size: Int
) : RecyclerView.Adapter<SpaceAdapter.SpaceViewHolder>() {

    class SpaceViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemSpaceBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_space, parent, false)
        binding = ItemSpaceBinding.bind(view)
        return SpaceViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SpaceViewHolder, position: Int) {
        binding.apply {
            ivAva.load(Helpers.dummyAva) {
                transformations(CircleCropTransformation())
                crossfade(true)
            }
            ivBgSpace.load(Helpers.dummyBg) { crossfade(true) }
            ivSectionOne.load(DummyData.getImg(0, 0)) {
                transformations(RoundedCornersTransformation(8f))
                crossfade(true)
            }
            ivSectionTwo.load(DummyData.getImg(0, 1)) {
                transformations(RoundedCornersTransformation(8f))
                crossfade(true)
            }
            ivSectionThree.load(DummyData.getImg(0, 2)) {
                transformations(RoundedCornersTransformation(8f))
                crossfade(true)
            }
        }
    }

    override fun getItemCount(): Int = size
}