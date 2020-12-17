package id.teachly.ui.managecontent.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ManageContentPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val tabTitle: List<String>
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return MangeContentFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitle[position]
    }

    override fun getCount(): Int = tabTitle.size
}