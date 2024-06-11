package com.mir.commonlibrary.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mir.commonlibrary.R
import com.mir.commonlibrary.databinding.FragmentMyCustomBottomSheetBinding

/**
Created by Shitab Mir on 11/6/24.
shitabmir@gmail.com
 **/
class MyCustomBottomSheetFragment : BottomSheetDialogFragment() {

 private lateinit var binding: FragmentMyCustomBottomSheetBinding
 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View {
  binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_custom_bottom_sheet, container, false)

  return binding.root
//  return inflater.inflate(R.layout.fragment_my_custom_bottom_sheet, container, false)
 }

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
   setOnShowListener {
    val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
    bottomSheet?.background = requireContext().getDrawable(R.drawable.bg_bottom_sheet)
   }
  }
 }

}