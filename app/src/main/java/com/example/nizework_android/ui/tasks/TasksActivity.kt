package com.example.nizework_android.ui.tasks

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nizework_android.R

class TasksActivity : AppCompatActivity() {
    private val viewModel: TasksViewModel by viewModels()
    private lateinit var adapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_tasks)

            val recyclerView = findViewById<RecyclerView>(R.id.tasksRecyclerView)
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            val btnBack = findViewById<ImageButton>(R.id.backButton)

            if (recyclerView == null) throw Exception("No encuentro 'tasksRecyclerView' en activity_tasks.xml")
            if (progressBar == null) throw Exception("No encuentro 'progressBar' en activity_tasks.xml")

            adapter = TasksAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            if (btnBack != null) {
                btnBack.setOnClickListener { finish() }
            }

            viewModel.tasks.observe(this) { taskList ->
                adapter.updateList(taskList)
            }

            viewModel.error.observe(this) { errorMsg ->
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            }

            viewModel.isLoading.observe(this) { isLoading ->
                if (progressBar != null) progressBar.isVisible = isLoading
            }

            viewModel.loadTasks()

        } catch (e: Exception) {
            val mensajeError = "ERROR: ${e.message}"
            Toast.makeText(this, mensajeError, Toast.LENGTH_LONG).show()
            println(mensajeError)
        }
    }
}