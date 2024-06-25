package com.mir.commonlibrary

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
Created by Shitab Mir on 4/6/24.
shitabmir@gmail.com
 **/
class AppLinkHandler(private val context: Context) {

 fun handleAppLink(intent: Intent) {
  val appLinkUri: Uri? = intent.data
  if (appLinkUri != null) {
   val path = appLinkUri.pathSegments

   // Handle different paths based on your applink structure
   when (path.getOrElse(0) { "" }) {
    "products" -> { // Redirect to products page
//     val productDetailsIntent = Intent(context, ProductListActivity::class.java)
//     context.startActivity(productDetailsIntent)
    }
    "about" -> { // Redirect to about page
//     val aboutIntent = Intent(context, AboutActivity::class.java)
//     context.startActivity(aboutIntent)
    }
    else -> { // Handle unknown paths (optional)
     // Show a toast or handle the case as needed
    }
   }
  }
 }
}