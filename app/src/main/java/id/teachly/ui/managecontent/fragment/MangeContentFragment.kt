package id.teachly.ui.managecontent.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.teachly.R
import id.teachly.data.Space
import id.teachly.data.Story
import id.teachly.databinding.FragmentManageContentBinding
import id.teachly.ui.base.fragment.home.HomeAdapter
import id.teachly.ui.managecontent.ManageContentViewModel
import id.teachly.ui.saved.GroupingAdapter
import id.teachly.utils.Helpers.toGrouping

class MangeContentFragment : Fragment() {


    private lateinit var binding: FragmentManageContentBinding
    private val model: ManageContentViewModel by viewModels()
    private var currentIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentIndex = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentManageContentBinding.bind(view)

        if (currentIndex == 1) {
            model.loadStories()
            model.stories.observe(viewLifecycleOwner, {
                populateDataStories(it)
            })
        } else {
            model.loadSpace()
            model.space.observe(viewLifecycleOwner, {
                populateDataSpace(it)
            })
        }

    }

    private fun populateDataSpace(space: List<Space>?) {
        binding.rvManageSection.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            adapter = GroupingAdapter(requireContext(), space?.toGrouping() ?: listOf())
        }
    }

    private fun populateDataStories(stories: List<Story>?) {
        binding.rvManageSection.apply {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
            adapter = HomeAdapter(requireContext(), stories ?: listOf())
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): MangeContentFragment {
            return MangeContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}