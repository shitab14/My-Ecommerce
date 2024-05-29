package com.mir.testermodule.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mir.testermodule.R
import com.mir.testermodule.databinding.ActivityDynamicViewBinding
import com.mir.testermodule.presentation.home.HomeFragment
import com.mir.testermodule.presentation.services.ServicesFragment
import com.mir.testermodule.presentation.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DynamicViewActivity : AppCompatActivity() {

    private val viewModel: DynamicViewActivityViewModel by viewModels()

    private lateinit var binding: ActivityDynamicViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_dynamic_view)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dynamic_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBottomNavigation(savedInstanceState)

    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    viewModel.selectTab(0)
                    true
                }
                R.id.navigation_services -> {
                    viewModel.selectTab(1)
                    true
                }
                R.id.navigation_settings -> {
                    viewModel.selectTab(2)
                    true
                }
                else -> false
            }
        }

        viewModel.selectedTab.observe(this) { tab ->
            val fragment = when (tab) {
                0 -> HomeFragment()
                1 -> ServicesFragment()
                2 -> SettingsFragment()
                else -> HomeFragment()
            }
            replaceFragment(fragment)
        }

        // Initialize with HomeFragment
        if (savedInstanceState == null) {
            viewModel.selectTab(0)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            val currentFragment = supportFragmentManager.fragments.last()
            when (currentFragment) {
                is HomeFragment -> viewModel.selectTab(0)
                is ServicesFragment -> viewModel.selectTab(1)
                is SettingsFragment -> viewModel.selectTab(2)
            }
        } else {
            super.onBackPressed()
        }
    }

}