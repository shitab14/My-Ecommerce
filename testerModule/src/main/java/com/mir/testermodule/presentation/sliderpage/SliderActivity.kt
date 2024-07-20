package com.mir.testermodule.presentation.sliderpage

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.mir.commonlibrary.viewpagertransitions.FadePageTransformer
import com.mir.testermodule.R
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class SliderActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: CircularAdapter

    private val items = listOf(
        "This will disappear in 5 seconds",
        "This will disappear in 2 seconds",
        "This will disappear in 6 seconds",
        "This will disappear in 3 seconds"
    )
    private var currentIndex = 0
    private var currentDelay = 4L // default delay in seconds

    private val executor = Executors.newSingleThreadScheduledExecutor()
    private var scheduledFuture: ScheduledFuture<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_slider)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewpager)
        adapter = CircularAdapter(items)
        viewPager.adapter = adapter

        viewPager.setPageTransformer(FadePageTransformer())
//        viewpager.setPageTransformer(DepthPageTransformer())
//        viewpager.setPageTransformer(ZoomOutPageTransformer())
        setupAutoScroll()
    }

    private fun setupAutoScroll() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentIndex = if (position > items.size) {
                    position % items.size
                } else {
                    position
                }
                super.onPageSelected(position)

                adjustScrollDelay()
            }
        })

        startAutoScroll()
    }

    private fun adjustScrollDelay() {
        // Cancel the current scheduled task
        scheduledFuture?.cancel(true)
        // Adjust the delay based on the current item
        currentDelay = getDelayForCurrentItem(currentIndex)
        // Schedule a new task with the adjusted delay
        startAutoScroll()
    }

    private fun getDelayForCurrentItem(index: Int): Long {
        // Return different delays based on the item index
        return when (index) {
            0 -> 5L
            1 -> 2L
            2 -> 6L
            3 -> 3L
            else -> 1L
        }
    }

    private fun startAutoScroll() {
        scheduledFuture = executor.scheduleWithFixedDelay({
            runOnUiThread {
                if (currentIndex == adapter.itemCount - 1) {
                    viewPager.setCurrentItem(0, true)
                } else {
                    viewPager.setCurrentItem(currentIndex + 1, true)
                }
            }
        }, currentDelay, currentDelay, TimeUnit.SECONDS)
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }

}