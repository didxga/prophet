package com.orange.prophet.ui

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.orange.prophet.ProphetApplication
import com.orange.prophet.R

class AccountFragment: Fragment(){

    private lateinit var mButtonMyQuizList: Button
    private lateinit var mButtonMyAccount: Button
    private lateinit var mButtonAbout: Button
    private lateinit var mTextViewUserName: TextView
    private lateinit var mTextViewEmail: TextView
    private lateinit var mButtonLogout: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val rootView: View = inflater.inflate(R.layout.fragment_me, container, false)

        mButtonMyQuizList = rootView.findViewById(R.id.fragment_me_button_my_quIz_list) as Button
        mButtonMyAccount= rootView.findViewById(R.id.fragment_me_button_account) as Button
        mButtonAbout = rootView.findViewById(R.id.fragment_me_button_about) as Button
        mButtonLogout = rootView.findViewById(R.id.fragment_me_button_logout) as Button
        mTextViewUserName = rootView.findViewById(R.id.fragment_me_text_username) as TextView
        mTextViewEmail = rootView.findViewById(R.id.fragment_me_text_email) as TextView

        mButtonMyQuizList.setOnClickListener(mButtonListener)
        mButtonMyAccount.setOnClickListener(mButtonListener)
        mButtonAbout.setOnClickListener(mButtonListener)

        return rootView
    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.fragment_me_button_my_quIz_list -> {
                //TODO: show my quiz list
            }
            R.id.fragment_me_button_account -> {

                if(ProphetApplication.instance().getAccount().token.isNotEmpty()) {
                    //the button is showed "More account info", then it will go to my account info activity
                    val intent = Intent(activity, AccountDetailActivity::class.java)
                    startActivity(intent)
                }else{
                    //go to login activity
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            R.id.fragment_me_button_term_policy -> {
                //TODO: go to term policy screen
            }

            R.id.fragment_me_button_about -> {
                //TODO: go to about screen

                }

            R.id.fragment_me_button_logout -> {
                //TODO:logout

                }
            }
        }

    
    override fun onResume() {
        super.onResume()

        val appInstance : ProphetApplication = ProphetApplication.instance()
        if (appInstance.getAccount().token.isNotEmpty())
        {
            // display the account info
            mButtonLogout.isInvisible = false
            mButtonMyAccount.text = "More account info"
            mTextViewUserName.text = appInstance.getAccount().user.username
            mTextViewEmail.text = appInstance.getAccount().user.email
        }else {
            // userToken is empty or shared preferences is null, my account button will be displayed as login
            mButtonLogout.isInvisible = true
            mButtonMyAccount.text = "Sign in/on"
            mTextViewUserName.text = "Not login"
            mTextViewEmail.text = ""
        }
    }
}


