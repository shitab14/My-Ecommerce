package com.mir.testermodule.presentation.videopage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.databinding.DataBindingUtil
import com.mir.commonlibrary.videoutil.VideoUtil
import com.mir.commonlibrary.base.BaseActivity
import com.mir.testermodule.R
import com.mir.testermodule.databinding.ActivityVideoBinding

class VideoActivity : BaseActivity<ActivityVideoBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_video)

        VideoUtil.setupVideoOnView(
            videoUrl = "https://shitab14.github.io/jsons/videoplayer/into_the_dead_gameplay.mp4",
            thumbnailUrl = "https://shitab14.github.io/jsons/dummyimage.jpg",
            autoPlay = false,
            allowPauseOnTap = true,
            videoView = binding.videoView1,
            thumbnailView = binding.thumbnailView1,
            enableRepeat = true,
            loaderView = binding.loaderView1
        )

        VideoUtil.setupVideoPlayer(
            videoUrl = "https://shitab14.github.io/jsons/videoplayer/into_the_dead_gameplay.mp4",
            thumbnailUrl = "https://shitab14.github.io/jsons/dummyimage.jpg",
            autoPlay = false,
            videoView = binding.videoView2,
            thumbnailView = binding.thumbnailView2,
            enableRepeat = false,
            loaderView = binding.loaderView2
        )


    }
}