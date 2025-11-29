package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class ActualizarDatos(
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellidouno")
    val apellidoUno: String,

    @SerializedName("apellidodos")
    val apellidoDos: String,

    @SerializedName("edad")
    val edad: Int,

    @SerializedName("telefono")
    val telefono: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("contra")
    val contrasenia: String,

    @SerializedName("tipo")
    val tipo: String,

    @SerializedName("numtarjeta")
    val numTarjeta: String,

    @SerializedName("expiracion")
    val expiracion: String
)