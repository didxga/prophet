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

            //TODO: if success, save the token and launch main activity
//            val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//
//            editor.putString("usertoken", )
//            editor.commit()
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
            return null
        }
    }

}