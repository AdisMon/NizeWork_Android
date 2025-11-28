package com.example.nizework_android.data.api

import com.example.nizework_android.data.model.RespuestaServicios
import com.example.nizework_android.data.model.DatosTarjeta
import com.example.nizework_android.data.model.GastoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiciosApiService {
    @GET("api/servicio/movil/{id}")
    suspend fun getServiciosPorUsuario(@Path("id") idUsuario: Int): Response<RespuestaServicios>

    @GET("api/auth/tarjeta/{id}")
    suspend fun getDatosTarjeta(@Path("id") idUsuario: Int): Response<DatosTarjeta>

    @POST("api/gasto/agregar")
    suspend fun registrarPago(@Body request: GastoRequest): Response<Void>
}