package com.mir.testermodule.practice

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
Created by Shitab Mir on 22/7/24.
shitabmir@gmail.com
 **/
fun main() {
 printDataInstantly()
 runOnMainThread()
}

fun runOnMainThread() {
 printDataAfterSixSeconds()
}
 fun printDataAfterSixSeconds() {
 CoroutineScope(Dispatchers.Unconfined).launch {
  delay(6000L)
  val data = "printDataAfterSixSeconds"
  print(data)
 }
 }

fun printDataInstantly(): String {
 val data = "printDataInstantly"
 print(data)
 return data
}