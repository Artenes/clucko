package io.github.artenes.clucko

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ClockInPagerAdapter(
    activity: FragmentActivity,
    private val model: MainViewModel
): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = model.daysCount

    override fun createFragment(position: Int): Fragment {
        val fragment = ClockInListFragment()
        fragment.arguments = bundleOf("index" to position)
        return fragment
    }

}