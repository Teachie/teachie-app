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
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.data.Category
import id.teachly.data.Users
import id.teachly.data.getNameOnly
import id.teachly.databinding.FragmentAddInterestBinding
import id.teachly.databinding.FragmentFillDataAcountBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreCategory
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.register.RegisterViewModel
import id.teachly.utils.Const
import id.teachly.utils.DummyData
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.getQueryChange
import id.teachly.utils.Helpers.setToolbarBack
import id.teachly.utils.Helpers.showError
import id.teachly.utils.Helpers.showView
import id.teachly.utils.Helpers.tag
import id.teachly.utils.Helpers.toTimeStamp
import java.util.*
import java.util.regex.Pattern

class FillDataAccountFragment : Fragment(), DialogInterestImpl {

    private lateinit var binding: FragmentFillDataAcountBinding
    private lateinit var dialogBinding: FragmentAddInterestBinding
    private lateinit var interestAdapter: AddInterestAdapter
    private val model: RegisterViewModel by viewModels()
    private val itemInterest = mutableListOf<Category>()
    private val currentInterestData = mutableListOf<Category>()

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
        interestAdapter = AddInterestAdapter(requireContext(), this)
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
                        isUsernameAvailable { available ->
                            if (available) {
                                if (isDataInterestExist()) {
                                    Log.d(
                                        requireActivity().tag(),
                                        "isDataInterestExist: ${itemInterest.size}"
                                    )
                                    createNewAccount(view)
                                } else {
                                    Log.d(
                                        requireActivity().tag(),
                                        "isDataInterestExist: ${itemInterest.size}"
                                    )
                                    showErrorInterest()
                                }
                            } else showErrorUsername(4)
                        }
                    } else showErrorUsername(3)
                } else Helpers.hideLoadingDialog();validateField()
            }
            btnAddInterests.setOnClickListener {
                showBottomDialog()
            }
        }
    }

    private fun showErrorInterest() {
        Helpers.hideLoadingDialog()
        Helpers.showToast(
            requireContext(),
            "Kamu belum memilih minat yang kamu suka"
        )
    }

    private fun showErrorUsername(data: Int) {
        Helpers.hideLoadingDialog()
        binding.tilUsername.showError(Helpers.errorEmptyFillDataMessage[data])
    }

    private fun createNewAccount(view: View) {
        binding.apply {
            val currentUser = Auth.getCurrentUser()
            model.createDataUser(
                Users(
                    currentUser?.uid,
                    edtUsername.text.toString(),
                    edtFullName.text.toString(),
                    currentUser?.email,
                    DummyData.getImg((0..1).random(), (0..4).random()),
                    edtDate.text.toString()
                        .toTimeStamp(Const.DateFormat.SIMPLE),
                    itemInterest.getNameOnly()
                )
            ) {
                if (it) {
                    Helpers.hideLoadingDialog()
                    view.findNavController()
                        .navigate(
                            FillDataAccountFragmentDirections
                                .actionFillDataAcountFragmentToUploadPhotoFragment()
                        )
                } else {
                    Helpers.hideLoadingDialog()
                }
            }
        }
    }

    private fun isDataInterestExist(): Boolean = itemInterest.size != 0

    private fun showBottomDialog() {
        Helpers.createBottomSheetDialog(
            requireContext(),
            R.layout.fragment_add_interest
        ) { view, dialog ->
            dialogBinding = FragmentAddInterestBinding.bind(view)
            dialogBinding.apply {

                rvInterest.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = interestAdapter
                }

                searchView.apply {
                    setOnQueryTextListener(getQueryChange {
                        val filter = currentInterestData.filter { category ->
                            category.name?.toLowerCase(Locale.getDefault())
                                ?.startsWith(it?.toLowerCase(Locale.getDefault()) ?: "") == true
                        }
                        populateItemInterest(filter)
                    })
                    setOnCloseListener {
                        populateItemInterest(currentInterestData)
                        return@setOnCloseListener false
                    }
                }

                FirestoreCategory.getAllCategory {
                    currentInterestData.apply {
                        clear()
                        addAll(it)
                    }
                    populateItemInterest(currentInterestData)
                }

                btnSave.setOnClickListener {
                    binding.chipGroup.removeAllViews()
                    itemInterest.forEach {
                        binding.chipGroup.addView(
                            Chip(binding.chipGroup.context)
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
    }

    private fun populateItemInterest(data: List<Category>) {
        interestAdapter.apply {
            clear()
            addData(data, itemInterest)
        }
    }

    private fun isNotEmpty(): Boolean {
        return !binding.edtFullName.text.isNullOrEmpty() &&
                !binding.edtUsername.text.isNullOrEmpty() &&
                !binding.edtDate.text.isNullOrEmpty()
    }

    private fun isUsernameValid(): Boolean {
        return binding.edtUsername.text.toString().split(" ").size == 1 && noSpecialChar()
    }

    private fun noSpecialChar(): Boolean {
        val pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        return !pattern.matcher(binding.edtUsername.text.toString()).find()
    }

    private fun isUsernameAvailable(isAvailable: (Boolean) -> Unit) {
        FirestoreUser.getUserByUsername(binding.edtUsername.text.toString()) {
            isAvailable(it.username == null)
        }
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
    }

    override fun removeItem(category: Category) {
        itemInterest.remove(category)
    }
}

