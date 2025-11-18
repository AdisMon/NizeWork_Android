package com.example.nizework_android.data.api

import com.example.nizework_android.data.model.Task
import com.example.nizework_android.data.model.TaskListResponse
import com.example.nizework_android.data.model.TaskResponse
import retrofit2.Call
import retrofit2.http.*

interface TasksApiService {

    // Obtener lista
    @GET("api/tareas")
    fun getListTareas(): Call<TaskListResponse>

    // Crear tarea
    @POST("api/tareas")
    fun crearTarea(@Body tarea: Task): Call<TaskResponse>

    // Borrar tarea
    @DELETE("api/tareas/{id}")
    fun borrarTarea(@Path("id") id: Int): Call<TaskResponse>

    // Actualizar tarea
    @PUT("api/tareas/{id}")
    fun actualizarTarea(@Path("id") id: Int, @Body tarea: Task): Call<TaskResponse>
}