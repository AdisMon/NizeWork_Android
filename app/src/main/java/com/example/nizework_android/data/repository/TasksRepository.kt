package com.example.nizework_android.data.repository

import com.example.nizework_android.data.api.RetrofitClient
import com.example.nizework_android.data.api.TasksApiService
import com.example.nizework_android.data.model.TaskListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksRepository {
    // Creamos la instancia de la API aquí
    private val apiService: TasksApiService = RetrofitClient.instance.create(TasksApiService::class.java)

    // Función para obtener tareas. Recibe una función "callback" para devolver el resultado.
    fun getTasks(onResult: (List<com.example.nizework_android.data.model.Task>?, String?) -> Unit) {
        apiService.getListTareas().enqueue(object : Callback<TaskListResponse> {
            override fun onResponse(call: Call<TaskListResponse>, response: Response<TaskListResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    // ¡Éxito! Devolvemos la lista
                    onResult(response.body()?.data, null)
                } else {
                    // Error del servidor
                    onResult(null, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TaskListResponse>, t: Throwable) {
                // Error de conexión
                onResult(null, "Fallo de conexión: ${t.message}")
            }
        })
    }
}