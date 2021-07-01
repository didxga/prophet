package com.orange.prophet.ui.api

import com.orange.prophet.ui.model.Question
import com.orange.prophet.ui.model.Quiz
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface QuestionEndpoint {

    @GET("question")
    fun getQuestion(@Query("quiz") page: Int): Call<ArrayList<Question>>

}