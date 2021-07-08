package com.orange.prophet.ui

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.orange.prophet.R

class AccountFragment: Fragment(){

    private lateinit var mMyQuizListButton: Button
    private lateinit var mMyAccountButton: Button
    private lateinit var mAboutButton: Button

    private lateinit var mToken:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val rootView: View = inflater.inflate(R.layout.account_fragment, container, false)

        mMyQuizListButton = rootView.findViewById(R.id.myquizlist_button) as Button
        mMyAccountButton = rootView.findViewById(R.id.myaccount_button) as Button
        mAboutButton = rootView.findViewById(R.id.about_button) as Button

        mMyQuizListButton.setOnClickListener(mButtonListener)
        mMyAccountButton.setOnClickListener(mButtonListener)
        mAboutButton.setOnClickListener(mButtonListener)

        val sharedPreferences =
            activity?.application?.getSharedPreferences("prophetApp", MODE_PRIVATE)
        var userToken: String ? = ""
        if (sharedPreferences != null) {
            userToken = sharedPreferences.getString("usertoken", "")
            //do nothing
            if (userToken?.isNotEmpty()!!) return rootView
        }
        //go to login activity
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)

        return rootView
    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.myquizlist_button -> {
                //TODO: show my quiz list
            }
            R.id.myaccount_button -> {
                //Go to my account deatil activity
                val intent = Intent(activity, AccountDetailActivity::class.java)
                startActivity(intent)
            }
            R.id.about_button -> {
                //TODO: go to about screen

                }
            }

        }
}


