package id.teachly.ui.publishsection

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
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.data.Category
import id.teachly.data.Space
import id.teachly.data.Story
import id.teachly.data.getNameOnly
import id.teachly.databinding.ActivityPublishSectionBinding
import id.teachly.databinding.FragmentAddInterestBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.ui.publishsection.choosespace.ChooseSpaceActivity
import id.teachly.ui.register.RegisterViewModel
import id.teachly.ui.register.fragments.filldata.AddInterestAdapter
import id.teachly.ui.register.fragments.filldata.DialogInterestImpl
import id.teachly.utils.DialogHelpers
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.hideLoadingDialog
import id.teachly.utils.Helpers.imgPicker
import id.teachly.utils.Helpers.showError
import id.teachly.utils.Helpers.showLoadingDialog
import id.teachly.utils.Helpers.showToast
import id.teachly.utils.Helpers.showView

class PublishSectionActivity : AppCompatActivity(), DialogInterestImpl {

    private lateinit var binding: ActivityPublishSectionBinding
    private lateinit var dialogBinding: FragmentAddInterestBinding
    private lateinit var content: String

    private val model: RegisterViewModel by viewModels()
    private val publishModel: PublishSectionViewModel by viewModels()

    private var imgUri: Uri? = "".toUri()
    private val itemInterest = mutableListOf<Category>()
    private val currentInterestData = mutableListOf<Category>()
    private var isPhotoReady = false
    private var currentSpace: Space? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublishSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Publish Bagian"
            setDisplayHomeAsUpEnabled(true)
        }

        content = intent.extras?.getString(EXTRA_DATA_STORY) ?: ""

        binding.apply {
            edtTitleSection.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            tvUpload.setOnClickListener {
                imgPicker(this@PublishSectionActivity, 3f, 2f) { uri ->
                    imgUri = uri
                    isPhotoReady = true
                    ivThumbnail.load(imgUri) { crossfade(true) }
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
            btnSave.setOnClickListener {
                showLoadingDialog(this@PublishSectionActivity, "Sedang merilis ceritamu")
                if (isDataValid()) {
                    if (isCategoryExist()) {
                        val story = Story(
                            title = edtTitleSection.text.toString(),
                            content = content,
                            spaceId = currentSpace?.spaceId,
                            categories = itemInterest.getNameOnly(),
                            writerId = Auth.getCurrentUser()?.uid,
                        )

                        if (currentSpace?.spaceId != null) publishModel.updateSpace(currentSpace!!)

                        if (isPhotoReady) publishModel.publishWithThumbnail(
                            story, imgUri ?: "".toUri(), this@PublishSectionActivity
                        )
                        else publishModel.publishStory(story, this@PublishSectionActivity)


                    } else {
                        hideLoadingDialog()
                        showToast(this@PublishSectionActivity, "Tambahkan kategori terlebih dahulu")
                    }
                } else validateTitle()
            }
        }
    }

    private fun showBottomDialog() {
        Helpers.createBottomSheetDialog(
            this,
            R.layout.fragment_add_interest
        ) { view, dialog ->
            dialogBinding = FragmentAddInterestBinding.bind(view)
            DialogHelpers.showBottomDialog(dialogBinding, dialog, this, currentInterestData)
        }
    }

    override fun populateItemInterest(data: List<Category>) {
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

    override fun onSave() {
        binding.chipGroup.removeAllViews()
        itemInterest.forEach {
            binding.chipGroup.addView(
                Chip(binding.chipGroup.context).apply {
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
        if (requestCode == REQ_CODE) showDataSpace(data)
    }

    private fun showDataSpace(data: Intent?) {
        val space = data?.getParcelableExtra<Space>(EXTRA_DATA_SPACE)
        binding.apply {
            btnAddSpace.text = "Ubah"
            ivAva.apply {
                load(space?.img) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(8f))
                }
                showView()
            }

            tvConnectedSpace.showView()
            tvSpace.apply {
                text = space?.title
                showView()
            }

            tvDscSpace.apply {
                text = space?.desc
                showView()
            }
            currentSpace = space
        }
    }

    private fun isDataValid(): Boolean = !binding.edtTitleSection.text.isNullOrEmpty()
    private fun isCategoryExist(): Boolean = itemInterest.size != 0
    private fun validateTitle() {
        hideLoadingDialog()
        binding.apply {
            if (edtTitleSection.text.isNullOrEmpty()) tilTitleSection.showError("Tidak boleh kosong")
        }
    }

    private fun validateError() = Helpers.validateError(binding.tilTitleSection)

    companion object {
        const val REQ_CODE = 101
        const val EXTRA_DATA_SPACE = "extra_data_space"
        const val EXTRA_DATA_STORY = "extra_data_story"
    }
}