package com.orange.prophet.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.BuildConfig
import com.orange.prophet.R
import com.orange.prophet.api.AccountEndPoint
import com.orange.prophet.ui.model.Account
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*

class AccountDetailActivity : AppCompatActivity() {

    private val mServerURL = BuildConfig.SERVER_URL
    private lateinit var mAccountEndpoint: AccountEndPoint
    private lateinit var mEmailText:EditText
    private lateinit var mUserNameText:EditText
    private lateinit var mFirstNameText:EditText
    private lateinit var mLastNameText:EditText
    private lateinit var mLogoutButton: Button
    private lateinit var mUpdateAccountButton: Button
    private lateinit var mChangePasswordButton: Button
    private lateinit var mToken:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)


        val retrofit: Retrofit = makeRetrofit()
        mAccountEndpoint = retrofit.create(AccountEndPoint::class.java)

        mEmailText = findViewById(R.id.account_detail_email_text)
        mUserNameText = findViewById(R.id.account_detail_username_text)
        mFirstNameText = findViewById(R.id.account_detail_firstname_text)
        mLastNameText = findViewById(R.id.account_detail_lastname_text)
        mLogoutButton = findViewById(R.id.account_detail_logout_button)
        mUpdateAccountButton = findViewById(R.id.account_detail_update_button)
        mChangePasswordButton = findViewById(R.id.account_detail_change_password_button)

        //get the account info from sharedPreferences
        val sharedPreferences = this.application?.getSharedPreferences("prophetApp", MODE_PRIVATE)
        if(sharedPreferences!=null) {
            mToken = sharedPreferences.getString("usertoken", "").toString()
        }
        val email:String? = sharedPreferences?.getString("email","")
        val username:String?  = sharedPreferences?.getString("username","")
        val firstname:String?  = sharedPreferences?.getString("firstname","")
        val lastname:String? = sharedPreferences?.getString("lastname","")

        if (email != null) {
            mEmailText.setText(email)
        }
        if (username != null) {
            mUserNameText.setText(username)
        }
        if (firstname != null) {
            mFirstNameText.setText(firstname)
        }
        if (lastname != null) {
            mLastNameText.setText(lastname)
        }

        //register listener
        mLogoutButton.setOnClickListener(mButtonListener)
        mUpdateAccountButton.setOnClickListener(mButtonListener)
        mChangePasswordButton.setOnClickListener(mButtonListener)

    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.account_detail_update_button -> {
                var email = ""
                email = mEmailText.text.toString()
                var username = ""
                username = mUserNameText.text.toString()
                var firstname = ""
                firstname= mFirstNameText.text.toString()
                var lastname = ""
                lastname= mLastNameText.text.toString()

                updateUserAccount(email,username,firstname,lastname,"Basic $mToken")

            }
            R.id.account_detail_change_password_button -> {

            }
            R.id.account_detail_logout_button -> {
                logout("Basic $mToken")
                // back to the quiz list screen
            }
        }
    }


    private fun updateUserAccount(email:String, username: String,firstname:String, lastname:String,token:String) {
        var call = mAccountEndpoint.updateAccount(email, username,firstname,lastname,token)
        call.enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                //get the token
                var accountInfo: Account? = response.body()
                if ((accountInfo != null)&&(accountInfo.token != null)) {
                    if (accountInfo.token.isNotEmpty()) {
                        //store the token
                        val sharedPreferences = application.getSharedPreferences("prophetApp", MODE_PRIVATE)
                        var editor = sharedPreferences.edit()
                        editor.putString("email", accountInfo.user.email)
                        editor.putString("username", accountInfo.user.username)
                        editor.putString("firstname", accountInfo.user.firstname)
                        editor.putString("lastname", accountInfo.user.lastname)
                        editor.putString("usertoken", accountInfo.token)

                        editor.commit()

                        Toast.makeText(
                            this@AccountDetailActivity,
                            "Successfully updated!",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                        return
                    }
                }
                //failed
                Toast.makeText(
                    this@AccountDetailActivity,
                    "Error occurred while update account, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet","Network error: "+ t.message)
                    Toast.makeText(this@AccountDetailActivity, "Network error"+t.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Orange_Prophet","Unexcepted error: "+ t.message)
                    Toast.makeText(this@AccountDetailActivity, "Unexcepted error"+t.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private  fun logout(token: String){
        val call = mAccountEndpoint.logout(token)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                //clear the sharedPreferences value
                val sharedPreferences = this@AccountDetailActivity.application.getSharedPreferences("prophetApp", MODE_PRIVATE)
                var editor = sharedPreferences.edit()
                editor.putString("email", "")
                editor.putString("username", "")
                editor.putString("firstname", "")
                editor.putString("lastname", "")
                editor.putString("usertoken", "")
                editor.commit()

                Toast.makeText(
                    this@AccountDetailActivity,
                    "Successfully logout!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
                return
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet", "Network error: " + t.message)
                    Toast.makeText(
                        this@AccountDetailActivity,
                        "Network error" + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.d("Orange_Prophet", "Unexcepted error: " + t.message)
                    Toast.makeText(
                        this@AccountDetailActivity,
                        "Unexcepted error" + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mServerURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}