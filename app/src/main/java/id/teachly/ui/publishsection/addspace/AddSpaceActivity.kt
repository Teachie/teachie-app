package id.teachly.ui.publishsection.addspace

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.data.Category
import id.teachly.data.Space
import id.teachly.data.getNameOnly
import id.teachly.databinding.ActivityAddSpaceBinding
import id.teachly.databinding.FragmentAddInterestBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreSpace
import id.teachly.repo.remote.firebase.storage.StorageUser
import id.teachly.ui.register.RegisterViewModel
import id.teachly.ui.register.fragments.filldata.AddInterestAdapter
import id.teachly.ui.register.fragments.filldata.DialogInterestImpl
import id.teachly.utils.DialogHelpers
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.hideLoadingDialog
import id.teachly.utils.Helpers.showError
import id.teachly.utils.Helpers.showLoadingDialog
import id.teachly.utils.Helpers.showToast
import id.teachly.utils.Helpers.showView

class AddSpaceActivity : AppCompatActivity(), DialogInterestImpl {

    private lateinit var binding: ActivityAddSpaceBinding
    private lateinit var dialogBinding: FragmentAddInterestBinding
    private lateinit var imgUrl: Uri

    private val model: RegisterViewModel by viewModels()

    private val itemInterest = mutableListOf<Category>()
    private val currentInterestData = mutableListOf<Category>()


    private var isPhotoExist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Buat Ruang"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.apply {

            edtName.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            edtDsc.addTextChangedListener(Helpers.getTextWatcher { validateError() })

            ivPickPhoto.setOnClickListener {
                Helpers.imgPicker(this@AddSpaceActivity, 1f, 1f) {
                    imgUrl = it
                    isPhotoExist = true
                    ivAva.load(it) { crossfade(true) }
                }
            }

            btnAddInterests.setOnClickListener { showBottomDialog() }
            btnSave.setOnClickListener {
                showLoadingDialog(this@AddSpaceActivity)
                if (isDataValid()) {
                    if (isPhotoExist) {
                        if (isCategoryExist()) {

                            StorageUser.storeImgSpace(imgUrl) { _, url ->
                                FirestoreSpace.createNewSpace(
                                    Space(
                                        userId = Auth.getCurrentUser()?.uid,
                                        title = edtName.text.toString(),
                                        desc = edtDsc.text.toString(),
                                        img = url,
                                        category = itemInterest.getNameOnly()
                                    )
                                ) {
                                    finish()
                                }
                            }
                        } else {
                            hideLoadingDialog()
                            showToast(this@AddSpaceActivity, "Tambahkan kategori terlebih dahulu")
                        }
                    } else {
                        hideLoadingDialog()
                        showToast(this@AddSpaceActivity, "Upload foto terlebih dahulu")
                    }
                } else validateField()
            }
        }
    }

    private fun isCategoryExist(): Boolean = itemInterest.size != 0
    private fun validateError() {
        binding.apply { Helpers.validateError(tilName, tilDsc) }
    }

    private fun validateField() {
        hideLoadingDialog()
        binding.apply {
            if (edtName.text.isNullOrEmpty()) tilName.showError("Tidak boleh kosong")
            if (edtDsc.text.isNullOrEmpty()) tilDsc.showError("Tidak boleh kosong")
        }
    }

    private fun isDataValid(): Boolean {
        return !binding.edtName.text.isNullOrEmpty() && !binding.edtName.text.isNullOrEmpty()
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
                this@AddSpaceActivity, this@AddSpaceActivity,
                data, itemInterest
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
                    model.loadImage(it.img ?: "", this@AddSpaceActivity) { img ->
                        chipIcon = img
                    }
                })
        }
        binding.chipGroup.showView()
    }
}