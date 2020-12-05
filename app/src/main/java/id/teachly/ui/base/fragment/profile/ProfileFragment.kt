package id.teachly.ui.base.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import id.teachly.R
import id.teachly.databinding.FragmentProfileBinding
import id.teachly.utils.Helpers

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.apply {
            ivAva.load(Helpers.dummyAva) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            ivTopic.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            ivTopic1.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            imageView6.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            imageView7.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            imageView8.load(Helpers.dummyTopic) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}