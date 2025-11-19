package com.example.nizework_android.data.model

import com.google.gson.annotations.SerializedName

data class TaskListResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<Task>
)
data class TaskResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: Task
)