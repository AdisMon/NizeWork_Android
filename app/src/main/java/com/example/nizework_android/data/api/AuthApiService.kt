package com.example.nizework_android.data.api

import com.example.nizework_android.data.model.Login
import com.example.nizework_android.data.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers

interface AuthApiService {
    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    fun setUser(@Body login: Login): Call<ResponseLogin>
}