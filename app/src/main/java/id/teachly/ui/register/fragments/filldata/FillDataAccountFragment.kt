package id.teachly.ui.register.fragments.filldata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.data.Category
import id.teachly.data.Users
import id.teachly.data.getNameOnly
import id.teachly.databinding.FragmentAddInterestBinding
import id.teachly.databinding.FragmentFillDataAcountBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreCategory
import id.teachly.ui.register.RegisterViewModel
import id.teachly.utils.Const
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.setToolbarBack
import id.teachly.utils.Helpers.showError
import id.teachly.utils.Helpers.showView
import id.teachly.utils.Helpers.tag
import id.teachly.utils.Helpers.toTimeStamp

class FillDataAccountFragment : Fragment(), DialogInterestImpl {

    private lateinit var binding: FragmentFillDataAcountBinding
    private val model: RegisterViewModel by viewModels()
    private val itemInterest = mutableListOf<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fill_data_acount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(
            requireActivity().tag(),
            "onViewCreated: argument from Create = ${arguments?.getInt("amount")}"
        )
        binding = FragmentFillDataAcountBinding.bind(view)
        binding.apply {
            toolbar.setToolbarBack { view.findNavController().popBackStack() }

            edtUsername.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            edtFullName.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            edtDate.addTextChangedListener(Helpers.getTextWatcher { validateError() })

            edtDate.setOnClickListener {
                Helpers.showDatePicker(requireActivity()) { date ->
                    Log.d(requireActivity().tag(), "showDatePicker: date = $date")
                    edtDate.setText(date)
                }
            }
            btnNext.setOnClickListener {
                Helpers.showLoadingDialog(requireContext(), "Sedang memperbaharui datamu")
                if (isNotEmpty()) {
                    if (isUsernameValid()) {
                        val currentUser = Auth.getCurrentUser()
                        model.createDataUser(
                            Users(
                                currentUser?.uid,
                                edtUsername.text.toString(),
                                edtFullName.text.toString(),
                                currentUser?.email,
                                null,
                                edtDate.text.toString().toTimeStamp(Const.DateFormat.SIMPLE),
                                itemInterest.getNameOnly()
                            )
                        ) {
                            if (it) {
                                Helpers.hideLoadingDialog()
                                view.findNavController()
                                    .navigate(FillDataAccountFragmentDirections.actionFillDataAcountFragmentToUploadPhotoFragment())
                            } else {
                                Helpers.hideLoadingDialog()
                            }
                        }
                    } else {
                        Helpers.hideLoadingDialog()
                        tilUsername.showError(Helpers.errorEmptyFillDataMessage[3])
                    }
                } else {
                    Helpers.hideLoadingDialog()
                    validateField()
                }
            }
            btnAddInterests.setOnClickListener {
                showBottomDialog()
            }
        }
    }

    private fun showBottomDialog() {
        val view = layoutInflater.inflate(R.layout.fragment_add_interest, null)
        val dialogBinding = FragmentAddInterestBinding.bind(view)
        val dialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialogTheme)
        dialog.setContentView(view)
        val behaviour = BottomSheetBehavior.from(view.parent as View)
            .apply {
                peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
                expandedOffset = binding.toolbar.height
            }
        dialogBinding.apply {
            rvInterest.apply {
                itemAnimator = DefaultItemAnimator()
                FirestoreCategory.getAllCategory {
                    adapter = AddInterestAdapter(
                        requireContext(),
                        this@FillDataAccountFragment, it, itemInterest
                    )
                }
            }
            btnSave.setOnClickListener {
                binding.chipGroup.removeAllViews()
                itemInterest.forEach {
                    binding.chipGroup.addView(Chip(binding.chipGroup.context)
                        .apply {
                            text = it.name
                            model.loadImage(it.img ?: "", requireContext()) { img ->
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

    private fun isNotEmpty(): Boolean {
        return !binding.edtFullName.text.isNullOrEmpty() &&
                !binding.edtUsername.text.isNullOrEmpty() &&
                !binding.edtDate.text.isNullOrEmpty()
    }

    private fun isUsernameValid(): Boolean {
        return binding.edtUsername.text.toString().split(" ").size == 1
    }

    private fun validateField() {
        binding.apply {
            if (edtFullName.text.isNullOrEmpty()) tilFullName.showError(Helpers.errorEmptyFillDataMessage[0])
            if (edtUsername.text.isNullOrEmpty()) tilUsername.showError(Helpers.errorEmptyFillDataMessage[1])
            if (edtDate.text.isNullOrEmpty()) tilDate.showError(Helpers.errorEmptyFillDataMessage[2])
        }
    }

    private fun validateError() {
        binding.apply {
            Helpers.validateError(tilFullName, tilUsername, tilDate)
        }
    }

    override fun addItem(category: Category) {
        itemInterest.add(category)
        Log.d(requireActivity().tag(), "addItem: $itemInterest")
    }

    override fun removeItem(category: Category) {
        itemInterest.remove(category)
        Log.d(requireActivity().tag(), "removeItem: $itemInterest")
    }
}

