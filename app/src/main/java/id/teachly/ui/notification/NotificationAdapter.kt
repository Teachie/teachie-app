package id.teachly.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.R
import id.teachly.databinding.ItemNotificationBinding
import id.teachly.utils.DummyData
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.hideView
import id.teachly.utils.Helpers.showView
import id.teachly.utils.Helpers.toDp


class NotificationAdapter(
    private val context: Context,
    private val notifications: List<Int>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemNotificationBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        binding = ItemNotificationBinding.bind(view)
        return NotificationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        binding.apply {
            when (notification) {
                0, 1 -> {
                    ivContent.apply {
                        showView()
                        load(Helpers.dummyBg) {
                            crossfade(true)
                            transformations(RoundedCornersTransformation(8f))
                        }
                    }
                    if (position == 1) ivContent.hideView()
                    else {
                        val newLayoutParam = tvMessage.layoutParams as ConstraintLayout.LayoutParams
                        newLayoutParam.marginEnd = 60.toDp(context)
                        tvMessage.layoutParams = newLayoutParam
                    }

                }
                2 -> btnFollow.showView()
            }

            ivAva.load(DummyData.getImg((0..1).random(), (0..3).random())) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            tvMessage.text = getNotificationMsg(notification)
        }
    }

    override fun getItemCount(): Int = notifications.size

    private fun getNotificationMsg(type: Int): String {
        return when (type) {
            0 -> "Menyukasi foto kamu"
            1 -> "Menyebut kamu adalam komentar"
            else -> "Mulai mengikuti kamu"
        }
    }
}