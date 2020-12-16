package id.teachly.ui.publishsection

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import coil.transform.RoundedCornersTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.data.Category
import id.teachly.databinding.ActivityPublishSectionBinding
import id.teachly.databinding.FragmentAddInterestBinding
import id.teachly.repo.remote.firebase.firestore.FirestoreCategory
import id.teachly.ui.publishsection.choosespace.ChooseSpaceActivity
import id.teachly.ui.register.RegisterViewModel
import id.teachly.ui.register.fragments.filldata.AddInterestAdapter
import id.teachly.ui.register.fragments.filldata.DialogInterestImpl
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.showView

class PublishSectionActivity : AppCompatActivity(), DialogInterestImpl {

    private lateinit var binding: ActivityPublishSectionBinding
    private lateinit var dialogBinding: FragmentAddInterestBinding
    private var imgUri: Uri? = "".toUri()
    private val itemInterest = mutableListOf<Category>()
    private val model: RegisterViewModel by viewModels()
    private val currentInterestData = mutableListOf<Category>()
    private var isPhotoReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublishSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Publish Bagian"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.apply {
            tvUpload.setOnClickListener {
                ImagePicker.with(this@PublishSectionActivity)
                    .crop(3f, 2f).start { resultCode, data ->
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                imgUri = data?.data
                                isPhotoReady = true
                                ivThumbnail.load(imgUri) { crossfade(true) }
                            }
                            ImagePicker.RESULT_ERROR -> {
                                Helpers.showToast(
                                    this@PublishSectionActivity,
                                    ImagePicker.getError(data)
                                )
                            }
                            else -> {
                                Helpers.showToast(this@PublishSectionActivity, "Task Canceled")
                            }
                        }
                    }
            }
            btnAddInterests.setOnClickListener { showBottomDialog() }
            btnAddSpace.setOnClickListener {
                startActivityForResult(
                    Intent(
                        this@PublishSectionActivity,
                        ChooseSpaceActivity::class.java
                    ), REQ_CODE
                )
            }
        }
    }

    private fun showBottomDialog() {
        Helpers.createBottomSheetDialog(
            this,
            R.layout.fragment_add_interest
        ) { view, dialog ->
            dialogBinding = FragmentAddInterestBinding.bind(view)
            dialogBinding.apply {


                searchView.apply {
                    setOnQueryTextListener(Helpers.getQueryChange {

                        FirestoreCategory.searchCategory(it ?: "") { category ->
                            populateItemInterest(category)
                        }

                    })
                    setOnCloseListener {
                        populateItemInterest(currentInterestData)
                        return@setOnCloseListener false
                    }
                }

                FirestoreCategory.getAllCategory {
                    populateItemInterest(it)
                }

                btnSave.setOnClickListener {
                    binding.chipGroup.removeAllViews()
                    itemInterest.forEach {
                        binding.chipGroup.addView(
                            Chip(binding.chipGroup.context)
                                .apply {
                                    text = it.name
                                    model.loadImage(
                                        it.img ?: "",
                                        this@PublishSectionActivity
                                    ) { img ->
                                        chipIcon = img
                                    }
                                })
                    }
                    binding.chipGroup.showView()
                    dialog.dismiss()
                }
                btnClose.setOnClickListener { dialog.dismiss() }
            }
            dialog.show()
        }
    }

    private fun populateItemInterest(data: List<Category>) {
        dialogBinding.rvInterest.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = AddInterestAdapter(
                this@PublishSectionActivity,
                this@PublishSectionActivity,
                data,
                itemInterest
            )
        }
        dialogBinding.rvInterest.adapter?.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun addItem(category: Category) {
        itemInterest.add(category)
    }

    override fun removeItem(category: Category) {
        itemInterest.remove(category)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) showDataSpace()
    }

    private fun showDataSpace() {
        binding.apply {
            btnAddSpace.text = "Ubah"
            ivAva.apply {
                load(Helpers.dummyAva) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(8f))
                }
                showView()
            }
            tvConnectedSpace.showView()
            tvSpace.showView()
            tvDscSpace.showView()
        }
    }

    companion object {
        const val REQ_CODE = 101
        const val EXTRA_DATA_SPACE = "extra_data_space"
    }
}