package id.teachly.ui.base.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.R
import id.teachly.databinding.FragmentHomeBinding
import id.teachly.ui.managecontent.ManageContentViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val model: ManageContentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        model.loadStories()

        model.stories.observe(viewLifecycleOwner, {
            binding.rvHome.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = HomeAdapter(requireContext(), it)
            }
        })


    }
}