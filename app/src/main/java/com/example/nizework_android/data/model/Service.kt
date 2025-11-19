package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class ServicioItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("empresa")
    val empresa: String,

    @SerializedName("fecha_limite")
    val fecha: String,

    @SerializedName("categoria")
    val categoria: String
)