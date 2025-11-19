package com.example.nizework_android.data.api

import com.example.nizework_android.data.model.RespuestaServicios
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiciosApiService {
    @GET("api/servicio/movil/{id}")
    suspend fun getServiciosPorUsuario(@Path("id") idUsuario: Int): Response<RespuestaServicios>
}