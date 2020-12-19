package id.teachly.ui.managecontent.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.teachly.R
import id.teachly.databinding.FragmentManageContentBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreStory
import id.teachly.ui.base.fragment.home.HomeAdapter
import id.teachly.ui.saved.GroupingAdapter

class MangeContentFragment : Fragment() {


    private lateinit var binding: FragmentManageContentBinding
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

        binding.rvManageSection.apply {
            layoutManager =
                if (currentIndex == 1) LinearLayoutManager(requireContext()) else GridLayoutManager(
                    requireContext(),
                    2
                )
            itemAnimator = DefaultItemAnimator()

            FirestoreStory.getStoryByUserId(Auth.getCurrentUser()?.uid ?: "") {
                adapter =
                    if (currentIndex == 1) HomeAdapter(requireContext(), it) else GroupingAdapter(
                        requireContext(),
                        5
                    )
            }

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