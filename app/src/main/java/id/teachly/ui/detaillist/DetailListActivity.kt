package id.teachly.ui.detaillist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.data.DetailData
import id.teachly.databinding.ActivityDetailListBinding
import id.teachly.ui.base.fragment.discussion.DiscussAdapter
import id.teachly.ui.base.fragment.home.HomeAdapter
import id.teachly.ui.detailtopic.SpaceAdapter
import id.teachly.ui.search.fragment.UserAdapter
import id.teachly.utils.Const

class DetailListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val data = intent.extras?.getParcelable(DETAIL_EXTRA) ?: DetailData()
        supportActionBar?.apply {
            title = data.title
            setDisplayHomeAsUpEnabled(true)
        }

        binding.rvData.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = when (data.type) {
                Const.List.SECTION -> {
                    HomeAdapter(this@DetailListActivity, listOf())
                }
                Const.List.SPACE -> SpaceAdapter(this@DetailListActivity, 10)
                Const.List.USER -> UserAdapter(this@DetailListActivity, listOf())
                else -> DiscussAdapter(this@DetailListActivity, listOf())
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DETAIL_EXTRA = "detail_extra"
    }
}