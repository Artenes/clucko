package io.github.artenes.clucko

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ClockInPagerAdapter(
    activity: FragmentActivity,
    private val time: Time
): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val currentTime = time.addDays(position)
        val fragment = ClockInListFragment()
        fragment.arguments = bundleOf("time" to currentTime.toEpochMilli())
        return fragment
    }

}