package com.orange.prophet.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R

class WebViewActivity: AppCompatActivity(){
        private lateinit var  mWebContent: WebView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_webview)
            supportActionBar?.hide();

            mWebContent = findViewById(R.id.legalnotice_webview)

            when (this.intent.getStringExtra("type")) {
                "legal_notice"->
                    mWebContent.loadUrl("file:///android_asset/web/legalNotice.html")
                "terms_of_use"->
                    mWebContent.loadUrl("file:///android_asset/web/CGUGooglePlaystore.html")

            }

            mWebContent.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
        }
    }
}