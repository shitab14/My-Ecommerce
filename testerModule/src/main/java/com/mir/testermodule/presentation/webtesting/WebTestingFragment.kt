package com.mir.testermodule.presentation.webtesting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mir.testermodule.R
import com.mir.testermodule.databinding.FragmentWebTestingBinding

/**
Created by Shitab Mir on 29/6/24.
shitabmir@gmail.com
 **/
class WebTestingFragment: Fragment() {
    private lateinit var binding: FragmentWebTestingBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_testing, container, false)

        binding.webviewContent.settings.javaScriptEnabled = true
        binding.webviewContent.webViewClient = WebViewClient()
        binding.webviewContent.addJavascriptInterface(WebAppInterface(), "Android")
        binding.webviewContent.loadUrl("file:///android_asset/index.html")
/*

        app/
        ├── src/
        │   ├── main/
        │   │   ├── assets/
        │   │   │   ├── index.html
        │   │   ├── java/
        │   │   ├── res/
        │   │   ├── AndroidManifest.xml
*/


        binding.btnDataEntry.setOnClickListener {
            binding.etDataEntry.text.let {
                sendDataToWeb(it.toString())
            }
        }

        return binding.root
    }

    private fun sendDataToWeb(data: String) {
        binding.webviewContent.evaluateJavascript(
            "javascript:receiveFromApp('$data');"
        ) { result ->
            // Process the result from the JavaScript execution
            Toast.makeText(
                requireContext(),
                "JavaScript returned: $result",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun receiveDataFromWeb(data: String) {
            binding.tvDataFromWeb.text = data
        }
    }

}