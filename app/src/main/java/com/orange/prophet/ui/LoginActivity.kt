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

    //[checkbox]remember the password
    private lateinit var mRememberPWCheckBox:CheckBox
    //[flag]remember the password
    private var mPasswordFlag = false
    //[checkbox]auto login
    private lateinit var mAutoLoginCheckBox:CheckBox
    //[flag] auto login
    private var mAutoLoginFlag = false

    private var userPassword: String? = ""

    private lateinit var mLoginButton: Button
    private lateinit var mRegisterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        mEmailText = findViewById(R.id.email_text)
        mPasswordText = findViewById(R.id.password_text)
        mRememberPWCheckBox = findViewById(R.id.remember_password)
        mAutoLoginCheckBox = findViewById(R.id.login_auto)
        mLoginButton = findViewById(R.id.login_button)
        mRegisterButton = findViewById(R.id.register_button)
        val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)

        if (sharedPreferences != null) {
            val userEmail = sharedPreferences.getString("email", "")
            userPassword = sharedPreferences.getString("password", "")
            mPasswordFlag = sharedPreferences.getBoolean("rememberpw", false)
            mAutoLoginFlag = sharedPreferences.getBoolean("autologin", false)
            mEmailText.setText(userEmail)
        }

        if (mPasswordFlag) {
            mRememberPWCheckBox.isChecked = true
            mPasswordText.setText(userPassword)
        }

        if (mAutoLoginFlag) {
            mAutoLoginCheckBox.isChecked = true
            val email = mEmailText.text.toString()
            val password = mPasswordText.getText().toString()
            login(email, password)
        } else{
            mAutoLoginCheckBox.isChecked = false
        }
        //register listener
        mLoginButton.setOnClickListener(mButtonListener)
        mRegisterButton.setOnClickListener(mButtonListener)
        mRememberPWCheckBox.setOnCheckedChangeListener{ _, isChecked ->
        if (isChecked){
            mPasswordFlag = true;
        }else{
            mPasswordFlag = false;
        }
        }
    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.login_button -> {
                //create SharedPreferences
                val email = mEmailText.text.toString()
                var password = mPasswordText.text.toString()
                val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("email", email)

                // TODO：password encrypt
                //  password = MD5Utils.getMD5(password)

                editor.putBoolean("rememberpw", mPasswordFlag)
                if(mPasswordFlag) {
                    editor.putString("password", password)
                }else{
                    editor.putString("password", "")
                }

                mAutoLoginFlag = mAutoLoginCheckBox.isChecked

                editor.putBoolean("autologin", mAutoLoginFlag)

                editor.commit()

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

            //if not success, show error
            //showResponse(response)

            //success, launch main activity
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            return null
        }
    }

}