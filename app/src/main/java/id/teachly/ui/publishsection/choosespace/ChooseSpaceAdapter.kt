package id.teachly.ui.publishsection.choosespace


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.databinding.ItemSpaceChooseBinding
import id.teachly.utils.Helpers

class ChooseSpaceAdapter(
    private val context: Context,
    private val size: Int
) : RecyclerView.Adapter<ChooseSpaceAdapter.ChooseSpaceViewHolder>() {

    class ChooseSpaceViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemSpaceChooseBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseSpaceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_space_choose, parent, false)
        binding = ItemSpaceChooseBinding.bind(view)
        return ChooseSpaceViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ChooseSpaceViewHolder, position: Int) {
        binding.apply {
            ivAva.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
            contentSpace.setOnClickListener {
                (context as ChooseSpaceActivity).addSpace("Data")
            }
        }
    }

    override fun getItemCount(): Int = size
}