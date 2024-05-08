package com.mir.myecommerce

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mir.myecommerce.common.NetworkUtil
import com.mir.myecommerce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var fadeAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.iBtnLanguageSwitch.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
        }

        fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in_out)
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

        if(NetworkUtil.isNetworkAvailable()) {
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