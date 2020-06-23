package com.worldofplay.demo.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("loginuser")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call<ResponseBody>

    companion object {
        operator fun invoke() : MyApi {
            return Retrofit.Builder()
                // We need to change the base URL here with the actual one when the backend can be
                // ready
                .baseUrl("https://95c66b45-8f7a-4417-b751-3cf8360f9d70.mock.pstmn.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}