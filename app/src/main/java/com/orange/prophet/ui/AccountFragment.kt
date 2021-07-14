package com.orange.prophet.ui

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.orange.prophet.BuildConfig
import com.orange.prophet.ProphetApplication
import com.orange.prophet.R
import com.orange.prophet.api.AccountEndPoint
import kotlinx.android.synthetic.main.fragment_me.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class AccountFragment: Fragment(){

    private val mServerURL = BuildConfig.SERVER_URL
    private lateinit var mAccountEndpoint: AccountEndPoint
    private lateinit var mButtonMyQuizList: Button
    private lateinit var mButtonMyAccountMore: ImageButton
    private lateinit var mButtonMyAccountLogin: Button
    private lateinit var mButtonAbout: Button
    private lateinit var mTextViewUserName: TextView
    private lateinit var mTextViewEmail: TextView
    private lateinit var mButtonLogout: Button
    private lateinit var mImageViewEmail: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val rootView: View = inflater.inflate(R.layout.fragment_me, container, false)

        mButtonMyQuizList = rootView.findViewById(R.id.fragment_me_button_my_quIz_list) as Button
        mButtonMyAccountMore= rootView.findViewById(R.id.fragment_me_button_account_more) as ImageButton
        mButtonMyAccountLogin= rootView.findViewById(R.id.fragment_me_button_account_login) as Button
        mButtonAbout = rootView.findViewById(R.id.fragment_me_button_about) as Button
        mButtonLogout = rootView.findViewById(R.id.fragment_me_button_logout) as Button
        mTextViewUserName = rootView.findViewById(R.id.fragment_me_text_username) as TextView
        mTextViewEmail = rootView.findViewById(R.id.fragment_me_text_email) as TextView
        mImageViewEmail = rootView.findViewById(R.id.fragment_me_imageView_Email) as ImageView

        mButtonMyQuizList.setOnClickListener(mButtonListener)
        mButtonMyAccountMore.setOnClickListener(mButtonListener)
        mButtonAbout.setOnClickListener(mButtonListener)
        mButtonLogout.setOnClickListener(mButtonListener)
        mButtonMyAccountLogin.setOnClickListener(mButtonListener)

        val retrofit: Retrofit = makeRetrofit()
        mAccountEndpoint = retrofit.create(AccountEndPoint::class.java)

        return rootView
    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.fragment_me_button_my_quIz_list -> {
                //TODO: show my quiz list
            }
            R.id.fragment_me_button_account_more -> {
                //the button is showed "More account info", then it will go to my account info activity
                val intent = Intent(activity, AccountDetailActivity::class.java)
                startActivity(intent)

            }
            R.id.fragment_me_button_account_login -> {
                //go to login activity
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)

            }
            R.id.fragment_me_button_term_policy -> {
                //TODO: go to term policy screen
            }

            R.id.fragment_me_button_about -> {
                //TODO: go to about screen

                }

            R.id.fragment_me_button_logout -> {
                logout()
                }
            }
        }

    private  fun logout(){
        val call = mAccountEndpoint.logout(ProphetApplication.instance().getAccount().token)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                //reset the account, clear the account data
                ProphetApplication.instance().resetAccount()

                Toast.makeText(
                    activity,
                    "Successfully logout!",
                    Toast.LENGTH_SHORT
                ).show()
                onResume()
                return
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet", "Network error: " + t.message)
                    Toast.makeText(
                        activity,
                        "Network error" + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.d("Orange_Prophet", "Unexcepted error: " + t.message)
                    Toast.makeText(
                        activity,
                        "Unexcepted error" + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        val appInstance : ProphetApplication = ProphetApplication.instance()
        if (appInstance.getAccount().token.isNotEmpty())
        {
            // display the account info
            mButtonLogout.isInvisible = false
            mButtonMyAccountMore.isInvisible = false
            mButtonMyAccountLogin.isInvisible = true
            mTextViewUserName.text = appInstance.getAccount().user.username
            mTextViewEmail.text = appInstance.getAccount().user.email
            mTextViewEmail.isInvisible = false
            mImageViewEmail.isInvisible = false
            mButtonMyQuizList.isInvisible = false
        }else {
            // userToken is empty or shared preferences is null, my account button will be displayed as login
            mButtonLogout.isInvisible = true
            mButtonMyAccountMore.isInvisible = true
            mButtonMyAccountLogin.isInvisible = false
            mTextViewUserName.text = "Not Sign In"
            mTextViewEmail.isInvisible = true
            mImageViewEmail.isInvisible = true
            mButtonMyQuizList.isInvisible = true
        }
    }

    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mServerURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}


