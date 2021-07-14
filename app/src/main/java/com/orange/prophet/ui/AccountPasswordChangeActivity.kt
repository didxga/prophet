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

class AccountPasswordChangeActivity : AppCompatActivity() {

    private val mServerURL = BuildConfig.SERVER_URL
    private lateinit var mAccountEndpoint: AccountEndPoint
    private lateinit var mPasswordText:TextView
    private lateinit var mPasswordConfirmText:TextView
    private lateinit var mChangePasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_password_edit)
        supportActionBar?.hide();

        val retrofit: Retrofit = makeRetrofit()
        mAccountEndpoint = retrofit.create(AccountEndPoint::class.java)

        mPasswordText = findViewById(R.id.account_edit_password_1_text)
        mPasswordConfirmText = findViewById(R.id.account_edit_password_2_text)

        mChangePasswordButton = findViewById(R.id.account_edit_password_deliver_button)

        //register listener
        mChangePasswordButton.setOnClickListener(mButtonListener)

    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.account_edit_password_deliver_button -> {
                val password:String = mPasswordText.text.toString()
                val passwordConfirm:String = mPasswordConfirmText.text.toString()
                if(password == passwordConfirm) {
                    updatePassword(ProphetApplication.instance().getAccount().user.email, password)
                }else{
                    //remind the password should be same
                    Toast.makeText(
                        this,
                        "Twice input are not same, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun updatePassword(email:String, password:String ) {

//        var call = mAccountEndpoint.changePassword(email)
//        call.enqueue(object : Callback<Account> {
//            override fun onResponse(call: Call<Account>, response: Response<Account>) {
//                //get the token
//                var accountInfo: Account? = response.body()
//
//                if (accountInfo != null) {
//                    //update the account value in application
//                    ProphetApplication.instance().setAccount(accountInfo)
//
//                    Toast.makeText(
//                        this@AccountPasswordChangeActivity,
//                        "Successfully updated!",
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                    finish()
//                    return
//
//                }else{
//                    Toast.makeText(
//                        this@AccountPasswordChangeActivity,
//                        "Error occurred while change password, please try again",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return
//                }
//            }
//
//            override fun onFailure(call: Call<Account>, t: Throwable) {
//                //${t.message}
//                if (t is IOException) {
//                    Log.d("Orange_Prophet","Network error: "+ t.message)
//                    Toast.makeText(this@AccountPasswordChangeActivity, "Network error"+t.message, Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.d("Orange_Prophet","Unexpected error: "+ t.message)
//                    Toast.makeText(this@AccountPasswordChangeActivity, "Unexcepted error"+t.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
    }

    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mServerURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onResume() {
        super.onResume()
        mPasswordText.text = ""
        mPasswordConfirmText.text = ""
    }
}