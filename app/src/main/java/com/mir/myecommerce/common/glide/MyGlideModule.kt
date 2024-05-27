package com.mir.myecommerce.common.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

/**
Created by Shitab Mir on 27/5/24.
shitabmir@gmail.com
 **/
@GlideModule
class MyGlideModule : AppGlideModule() {
 override fun applyOptions(context: Context, builder: GlideBuilder) {
  // Configure memory cache size (e.g., 20% of app memory)
  val memoryCacheSizeBytes = (Runtime.getRuntime().maxMemory() / 1024 / 5).toInt()
  builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))

  // Configure disk cache size and location
  val diskCacheSizeBytes = 1024 * 1024 * 100 // 100 MB
  builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes.toLong()))
 }

 override fun isManifestParsingEnabled(): Boolean {
  return false
 }
}