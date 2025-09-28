package com.example.nizework_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nizework_android.databinding.ActivityTasksBinding

class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        val taskList = createSampleData()
        val adapter = TasksAdapter(taskList)
        binding.tasksRecyclerView.adapter = adapter
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun createSampleData(): List<Task> {
        return listOf(
            Task("Tarea 1", "20/10/2025"),
            Task("Tarea 2", "21/10/2025"),
            Task("Tarea 3", "22/10/2025"),
            Task("Tarea 4", "23/10/2025"),
            Task("Tarea 5", "24/10/2025"),
            Task("Tarea 6", "25/10/2025"),
            Task("Tarea 7", "26/10/2025")
        )
    }
}