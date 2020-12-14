package id.teachly.ui.base.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.R
import id.teachly.databinding.FragmentExploreBinding


class ExploreFragment : Fragment() {

    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)
        exploreViewModel.apply {
            getFavoriteCategories()
            getAllCategories()
        }

        binding.apply {

            exploreViewModel.favoriteCategory.observe(viewLifecycleOwner, {
                rvTopicFav.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = ExploreAdapter(requireContext(), it)
                }
            })

            exploreViewModel.allCategory.observe(viewLifecycleOwner, {
                rvTopics.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = ExploreAdapter(requireContext(), it)
                }

            })
        }

    }


}