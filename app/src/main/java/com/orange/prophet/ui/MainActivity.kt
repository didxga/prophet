package com.orange.prophet.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orange.prophet.R


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide();

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val  b: Bundle? = intent.extras
        val isFromLoginActivity: Boolean = b?.getBoolean("fromLogin") ?: false
        if (isFromLoginActivity) {
            val bottomBar:BottomNavigationView = findViewById(R.id.navigationView)
            val item: MenuItem = bottomBar.getMenu().findItem(R.id.nav_me)
            item.isChecked = true
            openFragment(AccountFragment())
        } else {
            openFragment(QuizFragment())
        }
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
                val secondFragment = LeaderBoardFragment()
                openFragment(secondFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_me -> {
                val thirdFragment = AccountFragment()
                openFragment(thirdFragment)
//                val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
//                var userToken: String ? = ""
//                if (sharedPreferences != null) {
//                    userToken = sharedPreferences.getString("usertoken", "")
//                    if (userToken?.isNotEmpty()!!) {
//                        // go to my account fragment
//                        val thirdFragment = AccountFragment()
//                        openFragment(thirdFragment)
//                        return@OnNavigationItemSelectedListener true
//                    }
//                }
//                //go to login activity
//                val intent = Intent(this@MainActivity, LoginActivity::class.java)
//                intent.putExtra("callFrom","myaccount")
//                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
