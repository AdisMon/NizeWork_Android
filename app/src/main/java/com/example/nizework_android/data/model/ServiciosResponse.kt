package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class RespuestaServicios(
    @SerializedName("servicios")
    val servicios: List<ServicioItem>
)