package com.example.nizework_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class ResultLogin {
    @SerializedName("results")
    @Expose
    private var results: MutableList<Login?>? = null
}