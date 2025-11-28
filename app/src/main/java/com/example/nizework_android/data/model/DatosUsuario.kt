package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class DatosUsuario(
    // Cuerpo para mostrar los datos del usuario logueado
    @SerializedName("IdUsuarioPK")
    val idUsuario: Int,

    @SerializedName("Usuario")
    val email: String?,

    @SerializedName("Contrasenia")
    val contrasenia: String?,

    @SerializedName("Tipo")
    val tipo: String?,

    @SerializedName("NumTarjeta")
    val numTarjeta: String?,

    @SerializedName("Expiracion")
    val expiracion: String?,

    @SerializedName("Id_DatosMFK")
    val idDatosMFK: Int
)