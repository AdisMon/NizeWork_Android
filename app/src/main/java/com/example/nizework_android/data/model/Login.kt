package com.example.nizework_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Una Data Class crea automáticamente el constructor, getters, setters (si es 'var'),
// equals(), hashCode() y toString()
data class Login(
    @SerializedName("user")
    @Expose
    var user: String? = null, // Usar 'var' para que genere un setter si es necesario

    @SerializedName("password")
    @Expose
    var password: String? = null
)