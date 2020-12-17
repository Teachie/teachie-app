package id.teachly.ui.saved

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.data.DetailData
import id.teachly.databinding.ItemGropingBinding
import id.teachly.ui.detaillist.DetailListActivity
import id.teachly.utils.Const
import id.teachly.utils.Helpers

class GroupingAdapter(
    private val context: Context,
    private val size: Int
) : RecyclerView.Adapter<GroupingAdapter.GroupingViewHolder>() {

    class GroupingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemGropingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_groping, parent, false)
        binding = ItemGropingBinding.bind(view)
        return GroupingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GroupingViewHolder, position: Int) {
        binding.apply {
            ivHighlight.load(Helpers.dummyBg) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
            contentGrouping.setOnClickListener {
                context.startActivity(
                    Intent(context, DetailListActivity::class.java)
                        .putExtra(
                            DetailListActivity.DETAIL_EXTRA,
                            DetailData("Machine Learning", Const.List.SECTION)
                        )
                )
            }
        }
    }

    override fun getItemCount(): Int = size
}