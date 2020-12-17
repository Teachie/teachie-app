package id.teachly.ui.detaildiscussion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.R
import id.teachly.databinding.ItemCommentBinding
import id.teachly.utils.DummyData

class ResponseAdapter(
    private val context: Context,
    private val size: Int
) : RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder>() {

    class ResponseViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemCommentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        binding = ItemCommentBinding.bind(view)
        return ResponseViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        binding.imageView14.load(DummyData.getImg((0..1).random(), (0..3).random())) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount(): Int = size

}