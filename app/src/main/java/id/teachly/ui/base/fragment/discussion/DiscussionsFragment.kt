package id.teachly.ui.base.fragment.discussion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import id.teachly.R
import id.teachly.databinding.FragmentDiscussionsBinding
import id.teachly.ui.detaildiscussion.creatediscussion.CreateDiscussionActivity

class DiscussionsFragment : Fragment() {

    private val discussionsViewModel: DiscussionsViewModel by viewModels()
    private lateinit var binding: FragmentDiscussionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discussions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiscussionsBinding.bind(view)

        discussionsViewModel.getMyDiscussion()
        discussionsViewModel.discussion.observe(viewLifecycleOwner, {

            binding.apply {
                rvDiscuss.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = DiscussAdapter(requireContext(), it)
                }
                fabCreateDiscussion.setOnClickListener {
                    startActivity(Intent(requireContext(), CreateDiscussionActivity::class.java))
                }
            }

        })

    }
}