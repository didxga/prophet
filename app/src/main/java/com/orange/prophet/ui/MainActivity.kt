package com.orange.prophet.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orange.prophet.R
import org.codechimp.apprater.AppRater


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide();

        AppRater.setPackageName("com.orange.labs.intuitionnetwork");
        AppRater.app_launched(this);

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        openFragment(QuizFragment())
    }
    companion object {
        fun getIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.action = "data://" + System.currentTimeMillis()
            return intent
        }
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {

            R.id.nav_quiz -> {
                val firstFragment = QuizFragment()
                openFragment(firstFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_leaderboard -> {
                val secondFragment = LeaderBoardFragment()
                openFragment(secondFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_me -> {
                val thirdFragment = AccountFragment()
                openFragment(thirdFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
