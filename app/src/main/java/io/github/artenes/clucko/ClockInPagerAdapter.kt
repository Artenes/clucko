package io.github.artenes.clucko

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ClockInPagerAdapter(
    activity: FragmentActivity,
    private val time: Time
): FragmentStateAdapter(activity) {

    companion object {

        const val SIX_YEARS_RANGE = 2191
        const val BASE_DAY = 1095

    }

    override fun getItemCount(): Int = SIX_YEARS_RANGE //3 years range from left and right

    override fun createFragment(position: Int): Fragment {
        val realIndex = position - BASE_DAY
        val currentTime = time.addDays(realIndex)
        val fragment = ClockInListFragment()
        fragment.arguments = bundleOf("time" to currentTime.toEpochMilli())
        return fragment
    }

}