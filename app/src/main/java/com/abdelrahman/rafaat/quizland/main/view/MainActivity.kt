package com.abdelrahman.rafaat.quizland.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.setting))
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.home))
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.history))

        binding.viewPager.adapter =
            ViewPagerAdapter(this.lifecycle, supportFragmentManager, binding.tabLayoutView.tabCount)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayoutView.getTabAt(position)!!.select()
            }
        })

        binding.tabLayoutView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab?.position ?: 1
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
        binding.viewPager.currentItem = 1
    }
}