package com.example.nizework_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class ResponseLogin {
    @SerializedName("datos")
    @Expose
    val datos: List<Login>? = null
}