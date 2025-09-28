package com.example.nizework_android

// Un data class es una clase simple cuyo único propósito es guardar datos.
data class Service(
    val category: String,
    val logoResId: Int, // Usaremos un ID de recurso para el logo
    val serviceType: String,
    val serviceName: String,
    val dueDate: String
)