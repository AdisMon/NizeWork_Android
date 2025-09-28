package com.example.nizework_android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    private fun setupButtons() {
        // Botón para ir hacia atrás
        binding.backButton.setOnClickListener {
            finish() // Cierra esta actividad y vuelve a la anterior (HomeActivity)
        }

        // Botón para guardar cambios
        binding.saveChangesButton.setOnClickListener {
            // Aquí iría la lógica para leer los datos de los campos
            // y guardarlos (por ejemplo, en una base de datos o en la nube).
            // Por ahora, solo mostramos un mensaje de simulación.
        }
    }
}