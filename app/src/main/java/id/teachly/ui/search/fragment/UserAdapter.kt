package id.teachly.ui.search.fragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.R
import id.teachly.data.Users
import id.teachly.databinding.ItemUserBinding
import id.teachly.ui.detailuser.DetailUserActivity

class UserAdapter(
    private val context: Context,
    private val dataUsers: List<Users>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        binding = ItemUserBinding.bind(view)
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = dataUsers[position]
        binding.apply {
            ivAva.load(user.img) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvFullName.text = user.fullName
            tvUsername.text = user.username
            tvBio.text = user.bio ?: ""

            contentUser.setOnClickListener {
                context.startActivity(Intent(context, DetailUserActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int = dataUsers.size

}