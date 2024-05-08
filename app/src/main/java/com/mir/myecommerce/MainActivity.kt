package com.mir.myecommerce

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mir.myecommerce.base.BaseActivity
import com.mir.myecommerce.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
//    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var fadeAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupInitialization()
        setupClickListeners()
        setupLiveDataObservers()
        setupAndStartSplashAnimation()

    }

    override fun onResume() {
        super.onResume()
        viewModel.checkInternetConnection()
    }

    private fun setupLiveDataObservers() {
        viewModel.internetConnected.observe(this) { connected ->
            if(connected) {
                @SuppressLint("SetTextI18n")
                binding.tvInternet.text = "Connection Has"
                binding.tvInternet.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                @SuppressLint("SetTextI18n")
                binding.tvInternet.text = "Connection NOT Has"
                binding.tvInternet.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
        }
    }

    private fun setupAndStartSplashAnimation() {
        fadeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                // on Animation Start
            }

            override fun onAnimationEnd(p0: Animation?) {
                // on Animation End
                binding.clMainContent.visibility = View.VISIBLE
                binding.clSplashContent.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
                // on Animation Repeat
            }

        })
        binding.clSplashContent.startAnimation(fadeAnimation)
    }

    private fun setupInitialization() {
        fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in_out)
    }


    private fun setupClickListeners() {
        binding.iBtnLanguageSwitch.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
        }
    }


}