package com.mir.commonlibrary.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

/**
Created by Shitab Mir on 10/6/24.
shitabmir@gmail.com
 **/
object UIUtil {
// SHITAB TODO optimize
 fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
  Toast.makeText(context, message, duration).show()
 }

 fun showSnackBar(view: View, message: String, duration: Int = Snackbar.LENGTH_LONG) {
  Snackbar.make(view, message, duration).show()
 }

 fun showCustomDialogFragment(fragmentManager: FragmentManager, dialogFragment: androidx.fragment.app.DialogFragment, tag: String) {
  dialogFragment.show(fragmentManager, tag)
 }

 fun showCustomBottomSheetFragment(fragmentManager: FragmentManager, bottomSheetFragment: com.google.android.material.bottomsheet.BottomSheetDialogFragment, tag: String) {
  bottomSheetFragment.show(fragmentManager, tag)
 }

 fun showCustomDialog(
  context: Context,
  title: String,
  message: String,
  positiveButtonText: String = "OK",
  negativeButtonText: String? = null,
  onPositiveButtonClick: (() -> Unit)? = null,
  onNegativeButtonClick: (() -> Unit)? = null,
  cancelable: Boolean = true,
  dismissibleOnTouchOutside: Boolean = true
 ) {
  val builder = MaterialAlertDialogBuilder(context)
   .setTitle(title)
   .setMessage(message)
   .setPositiveButton(positiveButtonText) { _, _ -> onPositiveButtonClick?.invoke() }

  if (negativeButtonText != null) {
   builder.setNegativeButton(negativeButtonText) { _, _ -> onNegativeButtonClick?.invoke() }
  }

  val dialog = builder.create()
  dialog.setCancelable(cancelable)
  dialog.setCanceledOnTouchOutside(dismissibleOnTouchOutside)
  dialog.show()
 }

 fun showCustomBottomSheet(
  context: Context,
  layout: Int,
  cancelable: Boolean = true,
  dismissibleOnTouchOutside: Boolean = true
 ) {
  val bottomSheetDialog = BottomSheetDialog(context)
  val view = LayoutInflater.from(context).inflate(layout, null)
  bottomSheetDialog.setContentView(view)
  bottomSheetDialog.setCancelable(cancelable)
  bottomSheetDialog.setCanceledOnTouchOutside(dismissibleOnTouchOutside)
  bottomSheetDialog.show()
 }

 fun showDatePicker(
  fragmentManager: FragmentManager,
  onDateSelected: (Long) -> Unit,
  title: String = "Select Date",
  constraints: MaterialDatePicker.Builder<Long>.() -> Unit = {}
 ) {
  val builder = MaterialDatePicker.Builder.datePicker()
   .setTitleText(title)
  builder.constraints()
  val datePicker = builder.build()
  datePicker.addOnPositiveButtonClickListener { selection ->
   onDateSelected(selection)
  }
  datePicker.show(fragmentManager, "DATE_PICKER")
 }

 fun showTimePicker(
  fragmentManager: FragmentManager,
  onTimeSelected: (Int, Int) -> Unit,
  title: String = "Select Time",
  is24Hour: Boolean = false
 ) {
  val clockFormat = if (is24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
  val timePicker = MaterialTimePicker.Builder()
   .setTitleText(title)
   .setTimeFormat(clockFormat)
   .build()
  timePicker.addOnPositiveButtonClickListener {
   onTimeSelected(timePicker.hour, timePicker.minute)
  }
  timePicker.show(fragmentManager, "TIME_PICKER")
 }
}