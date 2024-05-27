package com.mir.myecommerce.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mir.myecommerce.R

/**
Created by Shitab Mir on 27/5/24.
shitabmir@gmail.com
 **/
object ImageUtil {

 fun loadImageByUrl(
  fragment: Fragment,
  supportsCache: Boolean,
  placeHolder: Int = R.drawable.ic_image_grey,
  errorView: Int = R.drawable.ic_error_grey,
  imageUrl: String,
  imageView: ImageView
 ) {

  Glide.with(fragment) // SHITAB TODO Optimize later
   .load(imageUrl)
   .placeholder(placeHolder)
   .error(errorView)
   .diskCacheStrategy(if(supportsCache) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE) // Cache all data (original and transformed)
   .onlyRetrieveFromCache(supportsCache)
   .skipMemoryCache(!supportsCache)
   .into(object : CustomTarget<Drawable>() {
    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
     imageView.setImageDrawable(resource) // Successfully loaded from cache
    }

    override fun onLoadCleared(placeholder: Drawable?) {
     // Clear resources if needed
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
     // If loading from cache fails, attempt to load from the network
     Glide.with(fragment)
      .load(imageUrl)
      .placeholder(placeHolder)
      .error(errorView)
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .into(imageView)
    }
   })

 }

}