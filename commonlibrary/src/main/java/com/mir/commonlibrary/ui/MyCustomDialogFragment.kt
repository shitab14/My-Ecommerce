package com.mir.commonlibrary.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mir.commonlibrary.R
import com.mir.commonlibrary.databinding.FragmentMyCustomDialogBinding

/**
Created by Shitab Mir on 11/6/24.
shitabmir@gmail.com
 **/
class MyCustomDialogFragment: DialogFragment() {

 private lateinit var binding: FragmentMyCustomDialogBinding
 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View {
  binding =
   DataBindingUtil.inflate(inflater, R.layout.fragment_my_custom_dialog, container, false)

  return binding.root
//  return inflater.inflate(R.layout.fragment_my_custom_dialog, container, false)
 }

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  return super.onCreateDialog(savedInstanceState).apply {
   window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
  }
 }

}