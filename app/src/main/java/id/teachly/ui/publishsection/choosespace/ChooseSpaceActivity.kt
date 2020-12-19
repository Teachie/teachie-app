package id.teachly.ui.publishsection.choosespace

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.data.Space
import id.teachly.databinding.ActivityChooseSpaceBinding
import id.teachly.ui.managecontent.ManageContentViewModel
import id.teachly.ui.publishsection.PublishSectionActivity
import id.teachly.ui.publishsection.addspace.AddSpaceActivity

class ChooseSpaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseSpaceBinding
    private val model: ManageContentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseSpaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Hubungkan Ruang"
            setDisplayHomeAsUpEnabled(true)
        }

        model.loadSpace()
        model.space.observe(this, {
            populateDataSpace(it)
        })


    }

    private fun populateDataSpace(space: List<Space>?) {
        binding.apply {
            rvChooseSpace.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = ChooseSpaceAdapter(this@ChooseSpaceActivity, space ?: listOf())
            }
            fabAddSpace.setOnClickListener {
                startActivity(Intent(this@ChooseSpaceActivity, AddSpaceActivity::class.java))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    fun addSpace(data: Space) {
        setResult(
            PublishSectionActivity.REQ_CODE,
            Intent().putExtra(PublishSectionActivity.EXTRA_DATA_SPACE, data)
        )
        finish()
    }
}