package com.orange.prophet.ui.api

import com.orange.prophet.ui.model.Question
import retrofit2.Call
import retrofit2.http.*


interface QuestionEndpoint {

    @GET("question")
    fun getQuestion(@Query("quiz") page: Int): Call<ArrayList<Question>>

    @FormUrlEncoded
    @POST("question/answer")
    fun answer(@Field("questionid") questionid:String, @Field("optionid") optionid:Int, @Header("Authorization") token:String): Call<Void>

    @GET("question/myanswer")
    fun getAnswer(@Query("questionid") questionid: String, @Header("Authorization") token:String): Call<Int>

}