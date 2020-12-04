package id.teachly.ui.register.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.teachly.R
import id.teachly.databinding.ItemInterestBinding

class AddInterestAdapter(
    private val context: Context,
    private val listInterest: List<String>
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
        binding.checkBox.text = listInterest[position]
    }

    override fun getItemCount(): Int = listInterest.size

}