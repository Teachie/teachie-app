package id.teachly.ui.register.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.teachly.R
import id.teachly.databinding.FragmentUploadPhotoBinding
import id.teachly.ui.base.MainActivity

class UploadPhotoFragment : Fragment() {

    private lateinit var binding: FragmentUploadPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadPhotoBinding.bind(view)
        binding.btnNext.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadPhotoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}