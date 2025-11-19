package com.example.nizework_android.data.api

import com.example.nizework_android.data.model.Task
import com.example.nizework_android.data.model.TaskListResponse
import com.example.nizework_android.data.model.TaskResponse
import retrofit2.Call
import retrofit2.http.*

interface TasksApiService {
    @GET("api/tareas/usuario/{id}")
    fun getTasks(@Path("id") userId: Int): Call<TaskListResponse>
}