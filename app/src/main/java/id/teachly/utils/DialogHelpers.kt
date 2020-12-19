package id.teachly.utils

import com.google.android.material.bottomsheet.BottomSheetDialog
import id.teachly.data.Category
import id.teachly.databinding.FragmentAddInterestBinding
import id.teachly.repo.remote.firebase.firestore.FirestoreCategory
import id.teachly.ui.register.fragments.filldata.DialogInterestImpl

object DialogHelpers {

    fun showBottomDialog(
        dialogBinding: FragmentAddInterestBinding,
        dialog: BottomSheetDialog,
        dialogImpl: DialogInterestImpl,
        currentInterestData: List<Category>
    ) {
        dialogBinding.apply {
            searchView.apply {
                setOnQueryTextListener(Helpers.getQueryChange {
                    FirestoreCategory.searchCategory(it ?: "") { category ->
                        dialogImpl.populateItemInterest(category)
                    }

                })
                setOnCloseListener {
                    dialogImpl.populateItemInterest(currentInterestData)
                    return@setOnCloseListener false
                }
            }

            FirestoreCategory.getAllCategory {
                dialogImpl.populateItemInterest(it)
            }

            btnSave.setOnClickListener {
                dialogImpl.onSave()
                dialog.dismiss()
            }
            btnClose.setOnClickListener { dialog.dismiss() }
        }
        dialog.show()
    }
}