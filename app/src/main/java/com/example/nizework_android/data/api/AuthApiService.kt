package com.example.nizework_android.data.api

import com.example.nizework_android.data.model.ActualizarDatos
import com.example.nizework_android.data.model.Login
import com.example.nizework_android.data.model.ResponseLogin
import com.example.nizework_android.data.model.ResponseUpdate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApiService {
    // Ruta para iniciar sesión en NizeWork
    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    fun setUser(@Body login: Login): Call<ResponseLogin>

    // Ruta para editar los datos del usuario en "Perfil"
    @Headers("Content-Type: application/json")
    @PUT("api/registro/editar/{id}")
    fun updateUserData(@Path("id") userId: Int, @Body updateData: ActualizarDatos):
            Call<ResponseUpdate>
}