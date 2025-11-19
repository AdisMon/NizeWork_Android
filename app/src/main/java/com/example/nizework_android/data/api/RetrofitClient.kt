package com.example.nizework_android.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL = "http://192.168.137.70:5000/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        // LE decimos que queremos ver TODO el contenido (Body)
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 2. Creamos el cliente HTTP y le ponemos el espía
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}