package com.orange.prophet

import android.app.Application
import com.orange.prophet.ui.model.Account
import com.orange.prophet.ui.model.User
import kotlin.properties.Delegates

class ProphetApplication : Application( ){

    private var mUser = User("","","","")
    private var mAccount = Account(mUser,"")
    private var mApp: ProphetApplication? = null

    companion object {
        private var instance: ProphetApplication by Delegates.notNull()
        fun instance() = instance
    }


    override fun onCreate() {
        super.onCreate()

        instance = this

        //get the token info
        val sharedPreferences = applicationContext.getSharedPreferences("prophetApp", MODE_PRIVATE)
        if (sharedPreferences != null) {
            mAccount.token = sharedPreferences.getString("usertoken", "").toString()
            mAccount.user.email = sharedPreferences.getString("email", "").toString()
            mAccount.user.username = sharedPreferences.getString("username", "").toString()
            mAccount.user.firstname = sharedPreferences.getString("firstname", "").toString()
            mAccount.user.lastname = sharedPreferences.getString("lastname", "").toString()
        }
    }

    fun setAccount(account:Account)
    {
        val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
        if(sharedPreferences!=null) {
            var editor = sharedPreferences.edit()
            editor.putString("email", account.user.email)
            editor.putString("username", account.user.username)
            editor.putString("firstname", account.user.firstname)
            editor.putString("lastname", account.user.lastname)
            editor.putString("usertoken", account.token)
            editor.commit()
        }
        mAccount = account

    }

    fun getAccount():Account{
        return mAccount;
    }

    fun resetAccount(){
        mAccount.token = ""
        mAccount.user.email = ""
        mAccount.user.username = ""
        mAccount.user.firstname = ""
        mAccount.user.lastname = ""
        val sharedPreferences = getSharedPreferences("prophetApp", MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
    }




}