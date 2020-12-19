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
import id.teachly.data.Grouping
import id.teachly.databinding.ItemGropingBinding
import id.teachly.ui.detaillist.DetailListActivity
import id.teachly.utils.Const
import id.teachly.utils.Helpers.showView

class GroupingAdapter(
    private val context: Context,
    private val dataGrouping: List<Grouping>
) : RecyclerView.Adapter<GroupingAdapter.GroupingViewHolder>() {

    class GroupingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemGropingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_groping, parent, false)
        binding = ItemGropingBinding.bind(view)
        return GroupingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GroupingViewHolder, position: Int) {
        val grouping = dataGrouping[position]

        binding.apply {
            ivHighlight.load(grouping.img) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
            tvName.text = grouping.title
            grouping.desc.let {
                if (it != null) tvDesc.apply {
                    text = it
                    showView()
                }
            }
            tvSection.text = buildString { append(grouping.section).append(" Cerita") }
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

    override fun getItemCount(): Int = dataGrouping.size
}