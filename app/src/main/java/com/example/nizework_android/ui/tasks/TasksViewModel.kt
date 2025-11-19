package com.example.nizework_android.ui.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nizework_android.data.model.Task
import com.example.nizework_android.data.repository.TasksRepository
import com.example.nizework_android.util.SessionManager
class TasksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TasksRepository()

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadTasks() {
        val context = getApplication<Application>().applicationContext
        val session = SessionManager(context)
        val userId = session.getUserId()
        if (userId == -1) {
            _error.value = "No se encontró usuario logueado. Por favor inicia sesión."
            return
        }
        _isLoading.value = true
        repository.getTasks(userId) { list, errorMsg ->
            _isLoading.value = false
            if (errorMsg != null) {
                _error.value = errorMsg!!
            } else {
                _tasks.value = list ?: emptyList()
            }
        }
    }
}