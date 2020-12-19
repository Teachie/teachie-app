package id.teachly.ui.detailuser

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.teachly.data.Users
import id.teachly.databinding.ActivityDetailUserBinding
import id.teachly.databinding.LayoutInfoSpaceBinding
import id.teachly.databinding.LayoutInfoUserBinding
import id.teachly.ui.detailuser.fragment.SectionsPagerAdapter
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.hideView
import id.teachly.utils.Helpers.showView

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var layoutUserBinding: LayoutInfoUserBinding
    private lateinit var layoutSpaceBinding: LayoutInfoSpaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        layoutUserBinding = binding.layoutUser
        layoutSpaceBinding = binding.layoutSpace
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        val type = intent.extras?.getInt(EXTRA_TYPE, 0)
        val user = intent.extras?.getParcelable<Users>(EXTRA_TYPE)

        if (type == 1) {
            layoutUserBinding.contentMain.hideView()
            layoutSpaceBinding.contentMain.showView()
            populateSpaceData()
        } else {
            populateUserData()
        }

        binding.apply {
            val sectionsPagerAdapter =
                SectionsPagerAdapter(this@DetailUserActivity, supportFragmentManager)
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

    }

    private fun populateUserData() {
        layoutUserBinding.ivAva.load(Helpers.dummyAva) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    private fun populateSpaceData() {
        layoutSpaceBinding.apply {
            ivAva.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
            ivAva2.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_TYPE = "extra_type"
    }
}