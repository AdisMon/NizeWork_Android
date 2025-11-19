package com.example.nizework_android.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nizework_android.data.model.Task
import com.example.nizework_android.data.repository.TasksRepository

class TasksViewModel : ViewModel() {

    private val repository = TasksRepository()
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadTasks() {
        _isLoading.value = true
        repository.getTasks { list, errorMsg ->
            _isLoading.value = false

            if (errorMsg != null) {
                _error.value = errorMsg
            } else {
                _tasks.value = list ?: emptyList()
            }
        }
    }
}