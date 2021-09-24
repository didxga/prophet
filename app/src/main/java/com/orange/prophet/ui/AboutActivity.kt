package com.orange.prophet.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R


class AboutActivity: AppCompatActivity() {

    private lateinit var  mAbout: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.hide();

        mAbout = findViewById(R.id.about_webview)
        mAbout.loadUrl("file:///android_asset/web/about.html")
        mAbout.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
    }
}