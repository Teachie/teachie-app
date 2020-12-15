package id.teachly.ui.notification

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Notifikasi"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.rvNotification.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = NotificationAdapter(
                this@NotificationActivity,
                listOf(0, 1, 1, 2, 1, 0, 2, 1, 0, 0, 2)
            )
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}