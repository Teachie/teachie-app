package id.teachly.ui.publishsection.choosespace


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.data.Space
import id.teachly.databinding.ItemSpaceChooseBinding

class ChooseSpaceAdapter(
    private val context: Context,
    private val spaces: List<Space>
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
        val space = spaces[position]
        binding.apply {
            ivAva.load(space.img) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }

            tvTitleSpace.text = space.title
            tvDescSpace.text = space.desc

            contentSpace.setOnClickListener {
                (context as ChooseSpaceActivity).addSpace(space)
            }
        }
    }

    override fun getItemCount(): Int = spaces.size
}