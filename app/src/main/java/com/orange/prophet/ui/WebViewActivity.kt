package com.orange.prophet.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R

class WebViewActivity: AppCompatActivity(){
        private lateinit var  mLegalNotice: WebView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_webview)
            supportActionBar?.hide();

            mLegalNotice = findViewById(R.id.legalnotice_webview)

            when (this.intent.getStringExtra("type")) {
                "legal_notice"->
                mLegalNotice.loadUrl("file:///android_asset/web/legalNotice.html")

            }

            mLegalNotice.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
        }
    }
}