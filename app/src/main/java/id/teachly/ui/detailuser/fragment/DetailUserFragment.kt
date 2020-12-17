package id.teachly.ui.detailuser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.R
import id.teachly.databinding.FragmentDetailUserBinding
import id.teachly.ui.base.fragment.home.HomeAdapter
import id.teachly.ui.detailtopic.SpaceAdapter


class DetailUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailUserBinding
    private var currentIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentIndex = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailUserBinding.bind(view)
        binding.rvManageUser.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = if (currentIndex == 1) HomeAdapter(requireContext(), 10) else SpaceAdapter(
                requireContext(),
                5
            )
        }
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): DetailUserFragment {
            return DetailUserFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}