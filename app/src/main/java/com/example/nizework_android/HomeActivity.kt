package com.example.nizework_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se eliminó enableEdgeToEdge() y el WindowInsetsListener para simplificar

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Los listeners de los botones se mantienen vacíos, listos para tu lógica
        setupButtons()
    }

    private fun setupButtons() {
        binding.profileButton.setOnClickListener {
            // Lógica para ir a la pantalla de perfil
        }

        binding.servicesButton.setOnClickListener {
            // Lógica para ir a la pantalla de servicios
        }

        binding.tasksButton.setOnClickListener {
            // Lógica para ir a la pantalla de tareas
        }

        binding.pantryButton.setOnClickListener {
            // Lógica para ir a la pantalla de despensa
        }
    }
}