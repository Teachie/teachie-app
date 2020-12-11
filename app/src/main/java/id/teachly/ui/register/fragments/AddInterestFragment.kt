package id.teachly.ui.register.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.R
import id.teachly.databinding.FragmentAddInterestBinding

class AddInterestFragment : Fragment() {

    private lateinit var binding: FragmentAddInterestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_interest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddInterestBinding.bind(view)

        val interestList = listOf(
            "Masak",
            "Teknologi",
            "Fisika",
            "Kimia",
            "Biologi",
            "Android",
            "iOS",
            "Kotlin",
            "Java",
            "Berenang",
            "Menembak",
            "Makeup"
        )


        binding.apply {
            rvInterest.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = AddInterestAdapter(requireContext(), interestList)
            }

            btnClose.setOnClickListener { view.findNavController().popBackStack() }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddInterestFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}