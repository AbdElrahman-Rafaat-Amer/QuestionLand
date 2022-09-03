package com.abdelrahman.rafaat.quizland.main.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdelrahman.rafaat.quizland.history.view.HistoryFragment
import com.abdelrahman.rafaat.quizland.setting.SettingsFragment


class ViewPagerAdapter(
    lifecycle: Lifecycle,
    fragmentManager: FragmentManager,
    private var tabsNumber: Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = tabsNumber

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SettingsFragment()
            1 -> ProfileFragment()
            2 -> HistoryFragment()
            else -> ProfileFragment()
        }
    }

}