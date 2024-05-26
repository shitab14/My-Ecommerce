package com.mir.myecommerce.common

import android.content.Context
import android.content.Intent
import kotlin.system.exitProcess
/**
Created by Shitab Mir on 24/5/24.
shitabmir@gmail.com
 **/
object RestartHelper {
 fun restartApp(context: Context) {
  // Create an intent to launch the main activity
  val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
  if (intent != null) {
   // Set flags to clear the back stack
   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

   // Start the launcher activity
   context.startActivity(intent)

   // Exit the current process
   exitProcess(0)
  }
 }
}