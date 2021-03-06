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
import id.teachly.databinding.ItemSpaceBinding
import id.teachly.ui.detailuser.DetailUserActivity
import id.teachly.utils.DummyData
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.toDp

class SpaceAdapter(
    private val context: Context,
    private val size: Int,
    private val type: Int? = 1
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
            if (type != 1) contentSpace.layoutParams = ViewGroup.LayoutParams(
                320.toDp(context),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

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

            contentSpace.setOnClickListener {
                context.startActivity(Intent(context, DetailUserActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int = size
}