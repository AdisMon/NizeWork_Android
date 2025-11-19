package com.example.nizework_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("user")
    @Expose
    var user: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null
)