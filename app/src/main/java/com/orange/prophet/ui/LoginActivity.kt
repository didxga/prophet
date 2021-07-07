package com.orange.prophet.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.BuildConfig
import com.orange.prophet.R
import com.orange.prophet.api.LoginEndpoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*

class LoginActivity : AppCompatActivity() {

    private val mServerURL = BuildConfig.SERVER_URL
    private lateinit var mLoginEndpoint: LoginEndpoint
    private lateinit var mEmailText:EditText
    private lateinit var mPasswordText:EditText
    private lateinit var mLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val retrofit: Retrofit = makeRetrofit()
        mLoginEndpoint = retrofit.create(LoginEndpoint::class.java)

        mEmailText = findViewById(R.id.email_text)
        mPasswordText = findViewById(R.id.password_text)
        mLoginButton = findViewById(R.id.login_button)

        //register listener
        mLoginButton.setOnClickListener(mButtonListener)

    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.login_button -> {
                //create SharedPreferences
                val email = this@LoginActivity.mEmailText.text.toString()
                val password = this@LoginActivity.mPasswordText.text.toString()
                login(email, password)
            }
        }
    }

    private fun login(email:String, password: String) {
        val call = mLoginEndpoint.register(email, password)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>
            ) {
                //get the token
                val token: String? = response.body()
                if (token != null) {
                    if (token.isNotEmpty()) {
                        //store the token
                        val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("usertoken", token)
                        editor.commit()

                        finish()
                    }
                }
                //failed
                Toast.makeText(
                    this@LoginActivity,
                    "Error occurred while Login/Register, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
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

}