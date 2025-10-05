package com.example.nizework_android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityTasksBinding

class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ya no necesitamos 'setupRecyclerView' ni 'createSampleData'
        setupButtons()
    }

    private fun setupButtons() {
        // Botón para volver a la pantalla anterior
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}