package com.orange.prophet.ui.api

import com.orange.prophet.ui.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RankEndpoint {

    @GET("rank")
    fun getRank(@Query("page") page: Int): Call<ArrayList<User>>

}