package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName
data class GastoRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_servicio") val idServicio: Int,
    @SerializedName("monto") val monto: Double
)
