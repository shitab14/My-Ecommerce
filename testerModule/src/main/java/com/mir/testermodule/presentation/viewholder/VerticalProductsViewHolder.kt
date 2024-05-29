package com.mir.testermodule.presentation.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mir.testermodule.data.dynamicviewdto.DynamicViews
import com.mir.testermodule.databinding.ViewHolderVerticalProductsBinding

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
class VerticalProductsViewHolder(private val binding: ViewHolderVerticalProductsBinding) : RecyclerView.ViewHolder(binding.root) {
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
        fun from(parent: ViewGroup): VerticalProductsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewHolderVerticalProductsBinding.inflate(layoutInflater, parent, false)
            return VerticalProductsViewHolder(binding)
        }
    }

}