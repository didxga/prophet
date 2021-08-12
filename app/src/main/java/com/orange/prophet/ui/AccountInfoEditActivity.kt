package com.orange.prophet.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.BuildConfig
import com.orange.prophet.ProphetApplication
import com.orange.prophet.R
import com.orange.prophet.api.AccountEndPoint
import com.orange.prophet.ui.model.Account
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*

class AccountInfoEditActivity : AppCompatActivity() {

    private val mServerURL = BuildConfig.SERVER_URL
    private lateinit var mAccountEndpoint: AccountEndPoint
    private lateinit var mUserNameText:TextView
    private lateinit var mFirstNameText:TextView
    private lateinit var mLastNameText:TextView
    private lateinit var mUpdateAccountButton: Button
    private var mToken:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info_edit)
        supportActionBar?.hide();

        val retrofit: Retrofit = makeRetrofit()
        mAccountEndpoint = retrofit.create(AccountEndPoint::class.java)


        mUserNameText = findViewById(R.id.account_edit_username_text)
        mFirstNameText = findViewById(R.id.account_edit_firstname_text)
        mLastNameText = findViewById(R.id.account_edit_lastname_text)
        mUpdateAccountButton = findViewById(R.id.account_edit_deliver_button)

        //register listener
        mUpdateAccountButton.setOnClickListener(mButtonListener)

    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.account_edit_deliver_button -> {
                var username = ""
                username = mUserNameText.text.toString()
                var firstname = ""
                firstname= mFirstNameText.text.toString()
                var lastname = ""
                lastname= mLastNameText.text.toString()

                mToken = ProphetApplication.instance().getAccount().token
                updateUserAccount(username,firstname,lastname,"Basic $mToken")
            }
            R.id.account_detail_change_password_button -> {

            }
        }
    }


    private fun updateUserAccount(username: String,firstname:String, lastname:String,token:String) {
        var call = mAccountEndpoint.updateAccount(username,firstname,lastname,token)
        call.enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                //get the token
                var accountInfo: Account? = response.body()

                if (accountInfo != null) {
                    //update the account value in application
                    ProphetApplication.instance().setAccount(accountInfo)

                    Toast.makeText(
                        this@AccountInfoEditActivity,
                        "Successfully updated!",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                    return

                }else {
                    //failed
                    Toast.makeText(
                        this@AccountInfoEditActivity,
                        "Error occurred while update account, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                    logout()

                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet","Network error: "+ t.message)
                    Toast.makeText(this@AccountInfoEditActivity, "Network error"+t.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Orange_Prophet","Unexcepted error: "+ t.message)
                    Toast.makeText(this@AccountInfoEditActivity, "Unexcepted error"+t.message, Toast.LENGTH_SHORT).show()
                }
            }

            //if error occur, logout

        })
    }

    private  fun logout(){
        val call = mAccountEndpoint.logout(ProphetApplication.instance().getAccount().token)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                //reset the account, clear the account data
                ProphetApplication.instance().resetAccount()

                Toast.makeText(
                    this@AccountInfoEditActivity,
                    "Some error occur, have to logout!",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                //${t.message}
                //reset the account, clear the account data
                ProphetApplication.instance().resetAccount()

                Toast.makeText(
                    this@AccountInfoEditActivity,
                    "Some error occur, have to logout!",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        })
    }

    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mServerURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onResume() {
        super.onResume()
        //get the account info
        val appInstance:ProphetApplication = ProphetApplication.instance()
        mToken = appInstance.getAccount().token

        mUserNameText.text = appInstance.getAccount().user.username
        mFirstNameText.text = appInstance.getAccount().user.firstname
        mLastNameText.text = appInstance.getAccount().user.lastname

    }

}