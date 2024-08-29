package com.mir.commonlibrary.imagelib

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mir.commonlibrary.PermissionManager
import com.mir.commonlibrary.R
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.util.Calendar

/**
Created by Shitab Mir on 27/5/24.
shitabmir@gmail.com
 **/
object ImageUtil {

 fun loadImageByUrl(
  context: Context,
  supportsCache: Boolean,
  placeHolder: Int = R.drawable.ic_image_grey,
  errorView: Int = R.drawable.ic_error_grey,
  imageUrl: String,
  imageView: ImageView
 ) {

  Glide.with(context) // SHITAB TODO Optimize & Move to seperate module later
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
     Glide.with(context)
      .load(imageUrl)
      .placeholder(placeHolder)
      .error(errorView)
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .into(imageView)
    }
   })

 }

 fun dpToPx(context: Context, dp: Int): Int {
  val density = context.resources.displayMetrics.density
  return (dp * density).toInt()
 }

 fun loadImageFromWeb(url: String): Drawable? {
  return try {
   val inputStream: InputStream = URL(url).content as InputStream
   Drawable.createFromStream(inputStream, "created")
  } catch (e: Exception) {
   e.printStackTrace()
   null
  }
 }

 fun loadGifByUrl(
  context: Context,
  imageView: ImageView,
  url: String,
  autoplay: Boolean,
  repeat: Boolean) {
  val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)
//  val drawableImageViewTarget = DrawableImageViewTarget(imageView)

  if (repeat) {
   Glide.with(context)
    .asGif()
    .load(url)
    .apply(requestOptions)
    .into(imageView)
  } else {
   Glide.with(context)
    .asGif()
    .load(url)
    .apply(requestOptions)
    .into(imageView)
    .clearOnDetach()
  }

  if (!autoplay) {
   imageView.setOnClickListener {
    Glide.with(context)
     .asGif()
     .load(url)
     .apply(requestOptions)
     .into(imageView)
   }
  } else {
   Glide.with(context)
    .asGif()
    .load(url)
    .apply(requestOptions)
    .into(imageView)
  }
 }

 var GalIntent: Intent? = null
 fun getImageFromGallery(activity: Activity) {
  if (PermissionManager(activity).checkAndRequestPermissionsForGallery()) {
   GalIntent = Intent(
    Intent.ACTION_PICK,
    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
   )

   activity.startActivityForResult(
    Intent.createChooser(GalIntent, "Select Image From Gallery"),
    PermissionManager.REQUEST_FOR_WRITE_INTERNAL
   )
  }
 }

 fun getImageUri(inContext: Context, inImage: Bitmap?): Uri? {
  val currentTime = Calendar.getInstance().time
  if (inImage != null) {
   val bytes = ByteArrayOutputStream()
   inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
   val path = MediaStore.Images.Media.insertImage(
    inContext.contentResolver, inImage,
    "Title - $currentTime", null
   )
   val parse = if (path != null) {
    Uri.parse(path)
   } else {
    null
   }
   return parse
  } else {
   return null
  }
 }

 fun resize(input: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
  var image = input
  if (maxHeight > 0 && maxWidth > 0) {
   image = Bitmap.createScaledBitmap(image, maxWidth, maxHeight, true)
   return image
  } else {
   return image
  }
 }

 fun getRealPathFromURI(uri: Uri?, context: Context): String {
  val cursor: Cursor? = uri?.let { context.contentResolver.query(it, null, null, null, null) }
  cursor!!.moveToFirst()
  val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
  return cursor.getString(idx).let {
   cursor.close()
  }.toString()
 }

}