package com.example.nizework_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

public class ResultLogin {
    @SerializedName("results")
    @Expose
    private var results: MutableList<Login?>? = null

    fun getResults(): MutableList<Login?>? {
        return results
    }

    fun setResults(results: MutableList<Login?>?) {
        this.results = results
    }
}