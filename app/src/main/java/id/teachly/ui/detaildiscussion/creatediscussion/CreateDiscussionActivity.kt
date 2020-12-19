package id.teachly.ui.detaildiscussion.creatediscussion

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.data.Category
import id.teachly.data.Discussion
import id.teachly.data.getNameOnly
import id.teachly.databinding.ActivityCreateDiscussionBinding
import id.teachly.databinding.FragmentAddInterestBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.ui.detaildiscussion.DetailDiscussionViewModel
import id.teachly.ui.register.RegisterViewModel
import id.teachly.ui.register.fragments.filldata.AddInterestAdapter
import id.teachly.ui.register.fragments.filldata.DialogInterestImpl
import id.teachly.utils.DialogHelpers.showBottomDialog
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.hideLoadingDialog
import id.teachly.utils.Helpers.showError
import id.teachly.utils.Helpers.showView

class CreateDiscussionActivity : AppCompatActivity(), DialogInterestImpl {

    private lateinit var binding: ActivityCreateDiscussionBinding
    private lateinit var dialogBinding: FragmentAddInterestBinding

    private val model: RegisterViewModel by viewModels()
    private val discussionModel: DetailDiscussionViewModel by viewModels()

    private var imgUri: Uri? = "".toUri()
    private val itemInterest = mutableListOf<Category>()
    private val currentInterestData = mutableListOf<Category>()
    private var isPhotoReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Buat diskusi baru"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.apply {
            edtTitleSection.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            edtQuestion.addTextChangedListener(Helpers.getTextWatcher { validateError() })

            tvUpload.setOnClickListener {
                Helpers.imgPicker(this@CreateDiscussionActivity, 3f, 2f) { uri ->
                    imgUri = uri
                    isPhotoReady = true
                    ivThumbnail.load(imgUri) { crossfade(true) }
                }
            }
            btnAddInterests.setOnClickListener { showBottomDialog() }
            btnSave.setOnClickListener {
                Helpers.showLoadingDialog(this@CreateDiscussionActivity)
                if (isDataValid()) {
                    if (isCategoryExist()) {
                        val discussion = Discussion(
                            writerId = Auth.getCurrentUser()?.uid,
                            title = edtTitleSection.text.toString(),
                            question = edtQuestion.text.toString(),
                            category = itemInterest.getNameOnly()
                        )
                        if (isPhotoReady) {
                            discussionModel.createNewDiscussionWithImage(
                                discussion, imgUri ?: "".toUri(), this@CreateDiscussionActivity
                            )
                        } else discussionModel.createNewDiscussion(
                            discussion,
                            this@CreateDiscussionActivity
                        )

                    } else {
                        hideLoadingDialog()
                        Helpers.showToast(
                            this@CreateDiscussionActivity,
                            "Tambahkan kategori terlebih dahulu"
                        )
                    }
                } else validateField()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun showBottomDialog() {
        Helpers.createBottomSheetDialog(
            this,
            R.layout.fragment_add_interest
        ) { view, dialog ->
            dialogBinding = FragmentAddInterestBinding.bind(view)
            showBottomDialog(dialogBinding, dialog, this, currentInterestData)
        }
    }

    private fun isCategoryExist(): Boolean = itemInterest.size != 0

    private fun isDataValid(): Boolean =
        !binding.edtTitleSection.text.isNullOrEmpty() && !binding.edtQuestion.text.isNullOrEmpty()

    private fun validateField() {
        hideLoadingDialog()
        binding.apply {
            if (edtTitleSection.text.isNullOrEmpty()) tilTitleSection.showError("Tidak boleh kosong")
            if (edtQuestion.text.isNullOrEmpty()) tilQuestion.showError("Tidak boleh kosong")
        }
    }

    private fun validateError() {
        binding.apply {
            Helpers.validateError(tilTitleSection, tilQuestion)
        }

    }

    override fun addItem(category: Category) {
        itemInterest.add(category)
    }

    override fun removeItem(category: Category) {
        itemInterest.remove(category)
    }

    override fun populateItemInterest(data: List<Category>) {
        dialogBinding.rvInterest.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = AddInterestAdapter(
                this@CreateDiscussionActivity,
                this@CreateDiscussionActivity,
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
                        this@CreateDiscussionActivity
                    ) { img ->
                        chipIcon = img
                    }
                })
        }
        binding.chipGroup.showView()
    }
}