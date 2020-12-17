package id.teachly.ui.detailuser.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


private val TAB_TITLES = listOf(
    "Cerita", "Ruang"
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return DetailUserFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence = TAB_TITLES[position]

    override fun getCount(): Int = TAB_TITLES.size
}