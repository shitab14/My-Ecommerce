package com.mir.testermodule.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mir.testermodule.data.dynamicviewdto.DynamicViews
import com.mir.testermodule.presentation.viewholder.HorizontalProductsViewHolder
import com.mir.testermodule.presentation.viewholder.StaticImageViewHolder
import com.mir.testermodule.presentation.viewholder.VerticalProductsViewHolder

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
class MultiTypeAdapter(private val items: List<DynamicViews>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

 companion object {
  const val NO_VIEW = 0
  const val STATIC_IMAGE_VIEW = 1
  const val HORIZONTAL_PRODUCTS_SECTION = 2
  const val VERTICAL_PRODUCTS_SECTION = 3
 }

 override fun getItemViewType(position: Int): Int {
  return when (items[position].viewType) {
   "staticImageSection" -> {
    STATIC_IMAGE_VIEW
   }
   "horizontalProductsSection" -> {
    HORIZONTAL_PRODUCTS_SECTION
   }
   "verticalProductsSection" -> {
    VERTICAL_PRODUCTS_SECTION
   }
   else -> {
    NO_VIEW
   }

  }

 }

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
  return when (viewType) {
   STATIC_IMAGE_VIEW -> {
//    val view = inflater.inflate(R.layout.view_holder_static_image, parent, false)
    StaticImageViewHolder.from(parent)
   }
   HORIZONTAL_PRODUCTS_SECTION -> {
//    val view = inflater.inflate(R.layout.view_holder_horizontal_products, parent, false)
    HorizontalProductsViewHolder.from(parent)
   }
   VERTICAL_PRODUCTS_SECTION -> {
//    val view = inflater.inflate(R.layout.view_holder_vertical_products, parent, false)
    VerticalProductsViewHolder.from(parent)
   }
   else -> throw IllegalArgumentException("Invalid view type")
  }
 }

 override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
  val item = items[position]
  when (holder) {
   is StaticImageViewHolder -> {
    // Bind data to StaticImageViewHolder
    holder.bind(item)
   }
   is HorizontalProductsViewHolder -> {
    // Bind data to HorizontalProductsViewHolder
    holder.bind(item)
   }
   is VerticalProductsViewHolder -> {
    // Bind data to VerticalProductsViewHolder
    holder.bind(item)
   }
  }
 }

 override fun getItemCount(): Int = items.size
}