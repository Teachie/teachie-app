package id.teachly.ui.register.fragments.createaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import id.teachly.R
import id.teachly.databinding.FragmentCreateAccountBinding
import id.teachly.ui.register.RegisterViewModel
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.setToolbarBack
import id.teachly.utils.Helpers.showError

class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentCreateAccountBinding
    private val model: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateAccountBinding.bind(view)

        binding.apply {
            toolbar.setToolbarBack { requireActivity().finish() }

            edtEmail.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            edtNewPassword.addTextChangedListener(Helpers.getTextWatcher { validateError() })
            edtConfirmPassword.addTextChangedListener(Helpers.getTextWatcher { validateError() })

            btnNext.setOnClickListener {
                Helpers.showLoadingDialog(requireContext(), "Sedang membuat akun kamu")
                if (isNotEmpty()) {
                    if (isPasswordMatch()) {
                        model.createAccount(
                            edtEmail.text.toString(),
                            edtNewPassword.text.toString()
                        ) { isSuccess, message ->
                            if (isSuccess) {
                                Helpers.hideLoadingDialog()
                                view.findNavController()
                                    .navigate(
                                        CreateAccountFragmentDirections
                                            .actionCreateAccountFragmentToFillDataAcountFragment(10)
                                    )
                            } else {
                                Helpers.hideLoadingDialog()
                                if (Helpers.errorLoginMessage.values.indexOf(message) < 2)
                                    binding.tilEmailNew.showError(message)
                                else binding.tilPasswordNew.showError(message)
                            }
                        }
                    } else {
                        tilPasswordConfirm.showError(Helpers.errorEmptyLoginMessage[2])
                        Helpers.hideLoadingDialog()
                    }
                } else {
                    validateField()
                    Helpers.hideLoadingDialog()
                }
            }
        }
    }

    private fun isNotEmpty(): Boolean {
        return !binding.edtEmail.text.isNullOrEmpty() &&
                !binding.edtNewPassword.text.isNullOrEmpty() &&
                !binding.edtConfirmPassword.text.isNullOrEmpty()
    }

    private fun isPasswordMatch(): Boolean {
        return binding.edtNewPassword.text.toString() == binding.edtConfirmPassword.text.toString()
    }

    private fun validateField() {
        binding.apply {
            if (edtEmail.text.isNullOrEmpty()) tilEmailNew.showError(Helpers.errorEmptyLoginMessage[0])
            if (edtNewPassword.text.isNullOrEmpty()) tilPasswordNew.showError(Helpers.errorEmptyLoginMessage[1])
            if (edtConfirmPassword.text.isNullOrEmpty()) tilPasswordConfirm.showError(Helpers.errorEmptyLoginMessage[1])
        }
    }

    private fun validateError() {
        binding.apply {
            Helpers.validateError(tilEmailNew, tilPasswordNew, tilPasswordConfirm)
        }
    }

}