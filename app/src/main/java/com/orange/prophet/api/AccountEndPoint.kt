package com.orange.prophet.api

import com.orange.prophet.ui.model.Account
import retrofit2.Call
import retrofit2.http.*

interface AccountEndPoint {

   @GET("user/log_out")
    fun logout(@Field("Header Authorization")token:String):Call<Void>

    @FormUrlEncoded
    @POST("user/updateAccount")
    fun updateAccount(@Field("email")email:String, @Field("username")username:String,  @Field("firstname")firstname:String, @Field("lastname")lastname:String,@Field("Header Authorization")token:String):Call<Account>

    @FormUrlEncoded
    @POST("user/register_or_login")
    fun register(@Field("email") email:String ,@Field("password")password:String ):Call<Account>

}
