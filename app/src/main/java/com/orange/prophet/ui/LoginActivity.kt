package com.orange.prophet.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R
import java.io.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mEmailText:EditText
    private lateinit var mPasswordText:EditText
    private lateinit var mLoginButton: Button
    private lateinit var mRegisterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        mEmailText = findViewById(R.id.email_text)
        mPasswordText = findViewById(R.id.password_text)
        mLoginButton = findViewById(R.id.login_button)
        mRegisterButton = findViewById(R.id.register_button)
        val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
        var userToken: String ? = ""
        if (sharedPreferences != null) {
            userToken = sharedPreferences.getString("usertoken", "")
        }

        if (userToken?.isNotEmpty()!!) {
            // auto login
            // TODO: send login request to the server, following code will be moved to the callback function
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }else{
            // show login screen
            //register listener
            mLoginButton.setOnClickListener(mButtonListener)
            mRegisterButton.setOnClickListener(mButtonListener)
        }
    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.login_button -> {
                //create SharedPreferences
                val email = mEmailText.text.toString()
                var password = mPasswordText.text.toString()

                login(email, password)
            }
            R.id.register_button -> {
                register()
            }
        }
    }

    private fun login(username: String, password: String) {
        val task = LoginTask()
        task.execute()
    }

    private fun register() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }


    private fun showResponse(response: String) {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, response, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    inner class LoginTask : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            //TODO: call server login api
            //communicate with server

            //TODO: if not success, show error
            //showResponse(response)

            //TODO: if success, save the token and launch main activity
//            val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//
//            editor.putString("usertoken", )
//            editor.commit()

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            return null
        }
    }

}