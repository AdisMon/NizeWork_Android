package com.example.nizework_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class ResponseLogin (
    @SerializedName("token")
    val token: String,

    // Aquí recibimos la lista de usuarios que manda tu base de datos
    @SerializedName("datos")
    val datos: List<DatosUsuario>?
)

data class DatosUsuario(
    @SerializedName("IdUsuarioPK") // Tal cual está en tu base de datos
    val idUsuario: Int,

    @SerializedName("Usuario")
    val email: String?,

    @SerializedName("Tipo")
    val tipo: String?
)