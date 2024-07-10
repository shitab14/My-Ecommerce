package com.mir.commonlibrary.viewpagertransitions

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

/**
Created by Shitab Mir on 9/7/24.
shitabmir@gmail.com
 **/
class ZoomOutPageTransformer : ViewPager2.PageTransformer {
 override fun transformPage(view: View, position: Float) {
  val pageWidth = view.width
  val pageHeight = view.height
  if (position < -1) {
   view.alpha = 0f
  } else if (position <= 1) {
   val scaleFactor = max(0.85f, 1 - abs(position))
   val vertMargin = pageHeight * (1 - scaleFactor) / 2
   val horzMargin = pageWidth * (1 - scaleFactor) / 2
   if (position < 0) {
    view.translationX = horzMargin - vertMargin / 2
   } else {
    view.translationX = -horzMargin + vertMargin / 2
   }
   view.scaleX = scaleFactor
   view.scaleY = scaleFactor
   view.alpha = (0.5f + ((scaleFactor - 0.85f) / (1 - 0.85f) * (1 - 0.5f)))
  } else {
   view.alpha = 0f
  }
 }
}
