package com.orange.prophet.ui.api

import com.orange.prophet.ui.model.Quiz
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface QuizEndpoint {

    @GET("quiz")
    fun getQuiz(@Query("page") page: Int): Call<ArrayList<Quiz>>

    @GET("quiz/my_participation")
    fun getMyQuizList(@Query("page") page: Int,@Header("Authorization")token:String): Call<ArrayList<Quiz>>
}