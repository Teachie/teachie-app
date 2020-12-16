package id.teachly.ui.register.fragments.uploadphoto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import id.teachly.R
import id.teachly.databinding.FragmentUploadPhotoBinding
import id.teachly.ui.base.MainActivity
import id.teachly.ui.register.RegisterViewModel
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.setToolbarBack

class UploadPhotoFragment : Fragment() {

    private lateinit var binding: FragmentUploadPhotoBinding
    private val model: RegisterViewModel by viewModels()
    private var imgUri: Uri? = "".toUri()
    private var isPhotoReady = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadPhotoBinding.bind(view)

        binding.apply {
            toolbar.setToolbarBack { view.findNavController().popBackStack() }
            btnNext.setOnClickListener {
                Helpers.showLoadingDialog(requireContext(), "Sedang mengunggah foto")
                if (isPhotoReady) {
                    model.uploadPhoto(imgUri ?: "".toUri()) { isSuccess ->
                        Helpers.hideLoadingDialog()
                        if (isSuccess) navigateToMain()
                    }
                } else navigateToMain()
            }
            tvUpload.setOnClickListener {
                ImagePicker.with(requireActivity())
                    .cropSquare().start { resultCode, data ->
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                imgUri = data?.data
                                isPhotoReady = true
                                btnNext.text = "Selesai"
                                ivAva.load(imgUri) { crossfade(true) }
                            }
                            ImagePicker.RESULT_ERROR -> {
                                Helpers.showToast(requireContext(), ImagePicker.getError(data))
                            }
                            else -> {
                                Helpers.showToast(requireContext(), "Task Canceled")
                            }
                        }
                    }
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }
}