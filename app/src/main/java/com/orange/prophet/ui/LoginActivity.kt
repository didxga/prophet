package com.orange.prophet.ui

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

class LoginActivity : AppCompatActivity() {

    private val mServerURL = BuildConfig.SERVER_URL
    private lateinit var mAccountEndpoint: AccountEndPoint
    private lateinit var mEmailText:EditText
    private lateinit var mPasswordText:EditText
    private lateinit var mLoginButton: Button
    private lateinit var mForgetPasswordText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar?.hide();

        val retrofit: Retrofit = makeRetrofit()
        mAccountEndpoint = retrofit.create(AccountEndPoint::class.java)

        mEmailText = findViewById(R.id.email_text)
        mPasswordText = findViewById(R.id.password_text)
        mLoginButton = findViewById(R.id.login_button)
        mForgetPasswordText = findViewById(R.id.forget_password_textview)

        //register listener
        mLoginButton.setOnClickListener(mOnClickListener)
        mForgetPasswordText.setOnClickListener(mOnClickListener)

    }

    private var mOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.login_button -> {
                //create SharedPreferences
                val email = this@LoginActivity.mEmailText.text.toString()
                val password = this@LoginActivity.mPasswordText.text.toString()
                login(email, password)
            }
            R.id.forget_password_textview->{
                if(this@LoginActivity.mEmailText.text.toString().isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Please input your email address!", Toast.LENGTH_LONG).show()
                }else{
                    forgotPassword(ProphetApplication.instance().getAccount().user.email)
                }
            }
        }
    }

    private fun forgotPassword(email:String) {

        var call = mAccountEndpoint.forgotPassword(email)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                val builder = AlertDialog.Builder(this@LoginActivity)
                builder.setMessage(R.string.acquire_password_message)
                builder.setPositiveButton(android.R.string.ok) { _, _ ->

                    this@LoginActivity.finish()
                }
                builder.create().show()
                return
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet","Network error: "+ t.message)
                    Toast.makeText(this@LoginActivity, "Network error"+t.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Orange_Prophet","Unexpected error: "+ t.message)
                    Toast.makeText(this@LoginActivity, "Unexcepted error"+t.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun login(email:String, password: String) {
        var call = mAccountEndpoint.register(email, password)
        call.enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.body() != null) {
                    //get the token
                    var accountInfo: Account? = response.body()

                    if (accountInfo != null) {
                        ProphetApplication.instance().setAccount(accountInfo)
                    }

                    finish()
                    return
                }else {
                    //failed

                    Toast.makeText(
                        this@LoginActivity,
                        response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet","Network error: "+ t.message)
                    Toast.makeText(this@LoginActivity, "Network error"+t.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Orange_Prophet","Unexcepted error: "+ t.message)
                    Toast.makeText(this@LoginActivity, "Unexcepted error"+t.message, Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()

    }
}