package com.mir.commonlibrary.videoutil

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.bumptech.glide.Glide

/**
Created by Shitab Mir on 30/6/24.
shitabmir@gmail.com
 **/

object VideoUtil {

 fun setupVideoOnView(
  videoUrl: String,
  thumbnailUrl: String,
  autoPlay: Boolean,
  allowPauseOnTap: Boolean,
  enableRepeat: Boolean,
  videoView: VideoView,
  thumbnailView: ImageView,
  loaderView: View
 ) {
  // Show loader view while loading thumbnail and preparing video
  loaderView.visibility = View.VISIBLE

  // Load thumbnail image using Glide
  Glide.with(videoView.context)
   .load(thumbnailUrl)
   .into(thumbnailView)

  // Set the video URL
  videoView.setVideoURI(Uri.parse(videoUrl))

  thumbnailView.setOnClickListener {
   thumbnailView.visibility = ImageView.GONE
   videoView.start()
  }
  videoView.setOnPreparedListener { mediaPlayer ->
   val videoRatio = mediaPlayer.videoWidth / mediaPlayer.videoHeight.toFloat()
   val screenRatio = videoView.width / videoView.height.toFloat()
   val scaleX = videoRatio / screenRatio
   if (scaleX >= 1f) {
    videoView.scaleX = scaleX
   } else {
    videoView.scaleY = 1f / scaleX
   }


   // Hide loader view when video is prepared
   loaderView.visibility = View.GONE
   if (autoPlay) {
    thumbnailView.visibility = ImageView.GONE
    videoView.start()
   }
  }

  // Allow pause on tap if required
  if (allowPauseOnTap) {
   videoView.setOnClickListener {
    if (videoView.isPlaying) {
     videoView.pause()
    } else {
     videoView.start()
    }
   }
  }

  // Enable repeat if required
  if (enableRepeat) {
   videoView.setOnCompletionListener {
    videoView.start()
   }
  }

  videoView.setMediaController(null)
 }

 fun setupVideoPlayer(
  videoUrl: String,
  thumbnailUrl: String,
  autoPlay: Boolean,
  enableRepeat: Boolean,
  videoView: VideoView,
  thumbnailView: ImageView,
  loaderView: View
 ) {
  // Show loader view while loading thumbnail and preparing video
  loaderView.visibility = View.VISIBLE

  // Load thumbnail image using Glide
  Glide.with(videoView.context)
   .load(thumbnailUrl)
   .into(thumbnailView)

  // Set the video URL
  videoView.setVideoURI(Uri.parse(videoUrl))

  if (!autoPlay) {
   thumbnailView.setOnClickListener {
    thumbnailView.visibility = ImageView.GONE
    videoView.start()
   }
  }

  videoView.setOnPreparedListener {
   // Hide loader view when video is prepared
   loaderView.visibility = View.GONE
   if (autoPlay) {
    thumbnailView.visibility = ImageView.GONE
    videoView.start()
   }
  }

  // Add media controller for full video player experience
  val mediaController = MediaController(videoView.context)
  mediaController.setAnchorView(videoView)
  videoView.setMediaController(mediaController)

  // Enable repeat if required
  if (enableRepeat) {
   videoView.setOnCompletionListener {
    videoView.start()
   }
  }
 }
}

/*

        <FrameLayout
            android:id="@+id/flVideo1"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintDimensionRatio="16:9"
            app:layout_constraintTop_toTopOf="parent"
            android:background="@android:color/black">

            <VideoView
                android:id="@+id/videoView1"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_gravity="center"
                android:scaleType="fitXY" />

            <ImageView
                android:id="@+id/loaderView1"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="center"
                android:src="@drawable/ic_menu_offline"
                android:visibility="gone" />

            <ImageView
                android:id="@+id/thumbnailView1"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="fitXY"/>
        </FrameLayout>


 */