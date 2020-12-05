package id.teachly.ui.base.fragment.discussion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.teachly.R

class DiscussionsFragment : Fragment() {

    private lateinit var discussionsViewModel: DiscussionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        discussionsViewModel =
            ViewModelProvider(this).get(DiscussionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_discussions, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        discussionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}