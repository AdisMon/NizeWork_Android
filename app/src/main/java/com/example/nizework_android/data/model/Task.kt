package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("IdTareasPK") val id: Int,
    @SerializedName("Nombre") val nombre: String,
    @SerializedName("Tipo") val tipo: String?,
    @SerializedName("Descripción") val descripcion: String?,
    @SerializedName("FechaL") val fechaL: String?,
    @SerializedName("Hora") val hora: String?,
    @SerializedName("Id_UsuariosFK") val idUsuario: Int?,
    @SerializedName("Recordatorio") val recordatorio: Recordatorio?,

    var isExpanded: Boolean = false,   // Para saber si está abierta
    var isCompleted: Boolean = false   // Para el checkbox
)

data class Recordatorio(
    @SerializedName("Requerido") val requerido: Boolean,
    @SerializedName("Fecha") val fecha: String?
)