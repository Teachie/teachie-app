package id.teachly.ui.register.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import id.teachly.R
import id.teachly.databinding.FragmentFillDataAcountBinding

class FillDataAcountFragment : Fragment() {

    private lateinit var binding: FragmentFillDataAcountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fill_data_acount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(this.tag, "onViewCreated: argument from Create = ${arguments?.getInt("amount")}")
        binding = FragmentFillDataAcountBinding.bind(view)
        binding.apply {
            btnNext.setOnClickListener {
                view.findNavController()
                    .navigate(FillDataAcountFragmentDirections.actionFillDataAcountFragmentToUploadPhotoFragment())
            }
            btnAddInterests.setOnClickListener {
                view.findNavController()
                    .navigate(FillDataAcountFragmentDirections.actionFillDataAcountFragmentToAddInterestFragment())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FillDataAcountFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
