package com.mir.testermodule.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.mir.commonlibrary.base.BaseActivity
import com.mir.testermodule.DateUtil
import com.mir.testermodule.R
import com.mir.testermodule.databinding.ActivityDynamicViewBinding
import com.mir.testermodule.presentation.home.HomeFragment
import com.mir.testermodule.presentation.home.HomePageCache
import com.mir.testermodule.presentation.services.ServicesFragment
import com.mir.testermodule.presentation.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DynamicViewActivity : BaseActivity<ActivityDynamicViewBinding>() {

    private val viewModel: DynamicViewActivityViewModel by viewModels()

//    private lateinit var binding: ActivityDynamicViewBinding
    private lateinit var adapter: DynamicViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dynamic_view)

        val fragments = listOf(HomeFragment(), ServicesFragment(), SettingsFragment())
        adapter = DynamicViewPagerAdapter(supportFragmentManager, fragments)
        binding.viewPager.adapter = adapter

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.navigation_services -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.navigation_settings -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }

        // Set up ViewPager page change listener
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                HomePageCache.lastCacheTimestamp.let { lastCacheTimestamp ->
                    if (lastCacheTimestamp != null && position == 0 &&
                        (DateUtil.getCurrentTimeInMillis() - lastCacheTimestamp) > HomePageCache.CACHE_INTERVAL) {
                        (fragments[0] as HomeFragment).fetchAPIData()
                    }

                }
            }
        })

        viewModel.selectedTab.observe(this) { tab ->
            binding.viewPager.currentItem = tab
        }

        // Initialize with First Fragment
        if (savedInstanceState == null) {
            binding.viewPager.currentItem = 0
            viewModel.selectTab(0)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewModel.selectTab(0)
            binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }
    }

}