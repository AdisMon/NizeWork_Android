package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class DatosTarjeta(
    @SerializedName("numero") val numero: String?,
    @SerializedName("expiracion") val expiracion: String?,
    @SerializedName("titular") val titular: String?
)
