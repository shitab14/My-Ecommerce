package com.mir.commonlibrary.viewpagertransitions

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
Created by Shitab Mir on 9/7/24.
shitabmir@gmail.com
 **/
class DepthPageTransformer : ViewPager2.PageTransformer {
 override fun transformPage(view: View, position: Float) {
  if (position <= -1 || position >= 1) {
   view.alpha = 0f
  } else if (position == 0f) {
   view.alpha = 1f
   view.translationX = 0f
   view.scaleX = 1f
   view.scaleY = 1f
  } else {
   view.alpha = 1 - abs(position)
   view.translationX = -position * view.width
   val scaleFactor = 0.75f + (1 - abs(position)) * 0.25f
   view.scaleX = scaleFactor
   view.scaleY = scaleFactor
  }
 }
}
