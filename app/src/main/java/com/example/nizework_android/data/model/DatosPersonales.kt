package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class DatosPersonales(
    @SerializedName("IdDatosMPK")
    val idDatosM: Int,

    @SerializedName("Nombre")
    val nombre: String?,

    @SerializedName("ApellidoUno")
    val apellidoUno: String?,

    @SerializedName("ApellidoDos")
    val apellidoDos: String?,

    @SerializedName("Apodo")
    val apodo: String?,

    @SerializedName("Edad")
    val edad: Int?,

    @SerializedName("Telefono")
    val telefono: String?,

    @SerializedName("Email")
    val email: String?
)