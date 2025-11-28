package com.example.nizework_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login(
    // Cuerpo para mandar los datos de inicio de sesión
    @SerializedName("IdUsuarioPK")
    @Expose
    val id: Int? = null,

    @SerializedName("user")
    @Expose
    var user: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null
)