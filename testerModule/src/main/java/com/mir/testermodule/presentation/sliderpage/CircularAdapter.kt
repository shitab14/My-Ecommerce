package com.mir.testermodule.presentation.sliderpage

import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mir.testermodule.R

/**
Created by Shitab Mir on 8/7/24.
shitabmir@gmail.com
 **/
class CircularAdapter(private val items: List<String>) : RecyclerView.Adapter<CircularAdapter.ViewHolder>() {

 class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val textView: TextView = itemView.findViewById(R.id.itemText)
 }

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
  val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider_layout, parent, false)
  return ViewHolder(view)
 }

 override fun onBindViewHolder(holder: ViewHolder, position: Int) {
  val actualPosition = position % items.size
  holder.textView.text = items[actualPosition]
  if (actualPosition % 2 == 0) {
   holder.textView.setBackgroundColor(Color.CYAN)
  } else {
   holder.textView.setBackgroundColor(Color.LTGRAY)
  }

//  val fadeIn = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f)
//  fadeIn.duration = 5000 // 5 second fade-in
//  fadeIn.start()
 }

 override fun getItemCount(): Int = items.size
}
