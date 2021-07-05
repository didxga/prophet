package com.orange.prophet.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orange.prophet.R


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#000000"))
        actionBar!!.setBackgroundDrawable(colorDrawable)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        openFragment(QuizFragment())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
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
                val secondFragment = QuizFragment()
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