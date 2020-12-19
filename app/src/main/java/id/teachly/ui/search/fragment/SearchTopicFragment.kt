package id.teachly.ui.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.R
import id.teachly.databinding.FragmentSearchTopicBinding
import id.teachly.ui.base.fragment.discussion.DiscussAdapter
import id.teachly.ui.base.fragment.home.HomeAdapter
import id.teachly.ui.detailtopic.SpaceAdapter

class SearchTopicFragment : Fragment() {

    private lateinit var binding: FragmentSearchTopicBinding
    private var currentIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentIndex = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchTopicBinding.bind(view)
        binding.rvSearch.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = when (currentIndex) {
                1 -> HomeAdapter(requireContext(), listOf())
                2 -> SpaceAdapter(requireContext(), 20)
                3 -> UserAdapter(requireContext(), listOf())
                else -> DiscussAdapter(requireContext(), 10)
            }
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): SearchTopicFragment {
            return SearchTopicFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}