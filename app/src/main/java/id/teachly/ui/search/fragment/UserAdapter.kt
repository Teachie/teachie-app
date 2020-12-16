package id.teachly.ui.search.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.R
import id.teachly.databinding.ItemUserBinding
import id.teachly.utils.DummyData

class UserAdapter(
    private val context: Context,
    private val size: Int
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        binding = ItemUserBinding.bind(view)
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        binding.ivAva.load(DummyData.getImg((0..1).random(), (0..3).random())) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount(): Int = size

}