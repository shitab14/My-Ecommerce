package com.mir.testermodule.presentation.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mir.testermodule.data.dynamicviewdto.DynamicViews
import com.mir.testermodule.databinding.ViewHolderStaticImageBinding

/**
Created by Shitab Mir on 28/5/24.
shitabmir@gmail.com
 **/
class StaticImageViewHolder(private val binding: ViewHolderStaticImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: DynamicViews) {

        // Apply Style
// SHITAB TODO
        // Show Content
        data.content?.image.let {
            it?.let { imageUrl ->
                /*ImageUtil.loadImageByUrl(
                    context = itemView.context,
                    supportsCache = true,
                    imageUrl = imageUrl,
                    imageView = binding.staticImage
                )*/
                binding.tvDummy.text = imageUrl
            }
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): StaticImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewHolderStaticImageBinding.inflate(layoutInflater, parent, false)
            return StaticImageViewHolder(binding)
        }
    }

}