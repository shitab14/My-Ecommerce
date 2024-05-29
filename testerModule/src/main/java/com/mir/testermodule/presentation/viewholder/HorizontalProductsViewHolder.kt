package com.mir.testermodule.presentation.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mir.testermodule.data.dynamicviewdto.DynamicViews
import com.mir.testermodule.databinding.ViewHolderHorizontalProductsBinding

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
class HorizontalProductsViewHolder (private val binding: ViewHolderHorizontalProductsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: DynamicViews) {

        // Apply Style
// SHITAB TODO
        // Show Content
        data.content?.products?.let { products ->
            binding.tvTitle.text = products.size.toString()

        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): HorizontalProductsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewHolderHorizontalProductsBinding.inflate(layoutInflater, parent, false)
            return HorizontalProductsViewHolder(binding)
        }
    }

}