package com.orange.prophet.api

import com.orange.prophet.ui.model.Quiz
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginEndpoint {

    @GET("user/register")
    fun register(@Query("email") email: String,@Query("password") password: String): Call<String>

}