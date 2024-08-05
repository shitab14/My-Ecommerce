package com.mir.testermodule.presentation.threadoperation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.mir.commonlibrary.base.BaseActivity
import com.mir.testermodule.R
import com.mir.testermodule.databinding.ActivityThreadOperationBinding

class ThreadOperationActivity : BaseActivity<ActivityThreadOperationBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_thread_operation)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_thread_operation)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}