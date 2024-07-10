package com.mir.commonlibrary.viewpagertransitions

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
Created by Shitab Mir on 9/7/24.
shitabmir@gmail.com
 **/
class FadePageTransformer : ViewPager2.PageTransformer {
 override fun transformPage(page: View, position: Float) {
  page.alpha = 1 - abs(position)
  page.translationX = -position * page.width
  page.translationZ = -abs(position)

  if (position <= -1 || position >= 1) {
   page.alpha = 0f
  } else if (position == 0f) {
   page.alpha = 1f
  }
 }
}
