package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin (
    @SerializedName("token")
    val token: String,

    @SerializedName("datos")
    val datos: List<DatosUsuario>,

    @SerializedName("datos_personales")
    val datosPersonales: List<DatosPersonales>,

    // Campo para tomar la contraseña en texto plano
    @SerializedName("contra_ingresada")
    val contraIngresada: String?
)