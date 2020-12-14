package id.teachly.ui.register.fragments.filldata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.teachly.R
import id.teachly.data.Category
import id.teachly.databinding.ItemInterestBinding

class AddInterestAdapter(
    private val context: Context,
    private val dialogInters: DialogInterestImpl,
    private val listInterest: List<Category>,
    private val currentList: List<Category>
) : RecyclerView.Adapter<AddInterestAdapter.AddInterestViewHolder>() {

    class AddInterestViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemInterestBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddInterestViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_interest, parent, false)
        binding = ItemInterestBinding.bind(view)
        return AddInterestViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AddInterestViewHolder, position: Int) {
        val category = listInterest[position]
        binding.checkBox.apply {
            text = category.name
            if (currentList.contains(category)) isChecked = true
            setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> dialogInters.addItem(category)
                    false -> dialogInters.removeItem(category)
                }
            }
        }
    }

    override fun getItemCount(): Int = listInterest.size

}