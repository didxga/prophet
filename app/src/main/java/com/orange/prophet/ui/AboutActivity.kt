package com.orange.prophet.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R


class AboutActivity: AppCompatActivity() {
    private lateinit var mMoreAppsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.hide();

        mMoreAppsButton = findViewById(R.id.MoreApps)
        mMoreAppsButton.setOnClickListener(mButtonListener)
    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.MoreApps -> {
                //show more apps
                val uri: Uri = Uri.parse("https://primezone.orange.com")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }
}