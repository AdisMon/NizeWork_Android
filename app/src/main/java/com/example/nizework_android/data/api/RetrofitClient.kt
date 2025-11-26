package com.example.nizework_android.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.68.107:5000/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Función para obtener la instancia del servicio para iniciar sesión
    fun getAuthService(): AuthApiService {
        return instance.create(AuthApiService::class.java)
    }
}