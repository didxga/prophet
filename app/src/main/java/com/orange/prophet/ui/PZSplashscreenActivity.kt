package com.orange.prophet.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R

class PZSplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val theme: Int = intent.getIntExtra("pz_theme", R.style.PZ_Splashscreen_Night)
        setTheme(theme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.oic_splashscreen)
        val start: Boolean = intent.getBooleanExtra("pz_nostart", true)
        if (start) {
            Handler().postDelayed(launchMainRunnable, 2000)
        }
    }

    private val launchMainRunnable = {
        val intent: Intent = MainActivity.getIntent(applicationContext)
        startActivity(intent)
        this.finish()
    }

    companion object {
        fun getLaunchIntent(context: Context, theme: Int): Intent {
            val intent = Intent(context, PZSplashscreenActivity::class.java)
            intent.putExtra("pz_nostart", false)
            intent.putExtra("pz_theme", theme)
            intent.action = "data://" + System.currentTimeMillis()
            return intent
        }
    }
}
