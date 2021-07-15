package com.orange.prophet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R


class AboutActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.hide();
    }
}