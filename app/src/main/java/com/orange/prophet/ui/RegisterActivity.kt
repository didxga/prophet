package com.orange.prophet.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.R
import java.io.*

class RegisterActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_register)

        mEmailText = findViewById(R.id.email_text)
        mPasswordText = findViewById(R.id.password_text)

        mRegisterButton = findViewById(R.id.register_button)

        mRegisterButton.setOnClickListener(mButtonListener)

    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.register_button -> {
                //create SharedPreferences
                val email = mEmailText.text.toString()
                var password = mPasswordText.text.toString()

                sendRegister(email, password)
            }
        }
    }

    private fun sendRegister(username: String, password: String) {
        val task = registerTask()
        task.execute()
    }

    private fun showResponse(response: String) {
        runOnUiThread {
            Toast.makeText(this@RegisterActivity, response, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    inner class registerTask: AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            //TODO: call server login api
            //communicate with server

            //if not success, show error
            //showResponse(response)

            //success, launch login activity
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            return null
        }
    }

}