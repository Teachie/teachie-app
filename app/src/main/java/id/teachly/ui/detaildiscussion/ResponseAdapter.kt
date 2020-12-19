package id.teachly.ui.detaildiscussion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.R
import id.teachly.data.Response
import id.teachly.databinding.ItemCommentBinding
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.utils.Const
import id.teachly.utils.Helpers.formatDate

class ResponseAdapter(
    private val context: Context,
    private val data: List<Response>
) : RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder>() {

    class ResponseViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemCommentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        binding = ItemCommentBinding.bind(view)
        return ResponseViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        val response = data[position]

        binding.apply {
            FirestoreUser.getUserById(response.writerId ?: "") {
                imageView14.load(it.img) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvName.text = it.fullName
            }
            tvTime.text = response.time?.formatDate(Const.DateFormat.SHORT)
            tvComment.text = response.comment
        }
    }

    override fun getItemCount(): Int = data.size

}