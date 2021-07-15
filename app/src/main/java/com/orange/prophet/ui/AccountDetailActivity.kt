package com.orange.prophet.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
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

class AccountDetailActivity : AppCompatActivity() {
    private val mServerURL = BuildConfig.SERVER_URL
    private lateinit var mAccountEndpoint: AccountEndPoint
    private lateinit var mEmailText:TextView
    private lateinit var mUserNameText:TextView
    private lateinit var mFirstNameText:TextView
    private lateinit var mLastNameText:TextView
    private lateinit var mUpdateAccountButton: Button
    private lateinit var mChangePasswordButton: Button
    private var mToken:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        supportActionBar?.hide();

        val retrofit: Retrofit = makeRetrofit()
        mAccountEndpoint = retrofit.create(AccountEndPoint::class.java)

        mEmailText = findViewById(R.id.account_detail_email_text)
        mUserNameText = findViewById(R.id.account_detail_username_text)
        mFirstNameText = findViewById(R.id.account_detail_firstname_text)
        mLastNameText = findViewById(R.id.account_detail_lastname_text)
        mUpdateAccountButton = findViewById(R.id.account_detail_update_button)
        mChangePasswordButton = findViewById(R.id.account_detail_change_password_button)

        //get the account info
        val appInstance:ProphetApplication = ProphetApplication.instance()
        mToken = appInstance.getAccount().token

        mEmailText.text = appInstance.getAccount().user.email
        mUserNameText.text = appInstance.getAccount().user.username
        mFirstNameText.text = appInstance.getAccount().user.firstname
        mLastNameText.text = appInstance.getAccount().user.lastname

        //register listener
        mUpdateAccountButton.setOnClickListener(mButtonListener)
        mChangePasswordButton.setOnClickListener(mButtonListener)

    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.account_detail_update_button -> {
                //start account edit activity
                val intent = Intent(this, AccountInfoEditActivity::class.java)
                startActivity(intent)
            }
            R.id.account_detail_change_password_button -> {
//                //start change password activity
//                val intent = Intent(this, AccountPasswordChangeActivity::class.java)
//                startActivity(intent)
                updatePassword(mEmailText.text.toString());
            }
        }
    }
    override fun onResume() {
        super.onResume()
        //get the account info
        val appInstance:ProphetApplication = ProphetApplication.instance()
        mToken = appInstance.getAccount().token

        mEmailText.text = appInstance.getAccount().user.email
        mUserNameText.text = appInstance.getAccount().user.username
        mFirstNameText.text = appInstance.getAccount().user.firstname
        mLastNameText.text = appInstance.getAccount().user.lastname

    }

    private fun updatePassword(email:String) {

        var call = mAccountEndpoint.changePassword(email)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                val builder = AlertDialog.Builder(this@AccountDetailActivity)
                builder.setMessage(R.string.acquire_password_message)
                builder.setPositiveButton(android.R.string.ok) { _, _ ->

                    this@AccountDetailActivity.finish()
                }
                builder.create().show()

                    return
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet","Network error: "+ t.message)
                    Toast.makeText(this@AccountDetailActivity, "Network error"+t.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Orange_Prophet","Unexpected error: "+ t.message)
                    Toast.makeText(this@AccountDetailActivity, "Unexcepted error"+t.message, Toast.LENGTH_SHORT).show()
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