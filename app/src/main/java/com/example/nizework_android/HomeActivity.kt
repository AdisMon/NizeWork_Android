package com.example.nizework_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nizework_android.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- INICIO DE LA MODIFICACIÓN ---
        // Volvemos a activar el modo de pantalla completa para un look moderno
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aplicamos el padding para que el contenido no quede detrás de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // --- FIN DE LA MODIFICACIÓN ---

        // Los listeners de los botones ahora tendrán lógica
        setupButtons()
    }

    private fun setupButtons() {
        // Lógica para ir a la pantalla de perfil
        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Lógica para la pantalla de servicios (con mensaje temporal)
        binding.servicesButton.setOnClickListener {
            val intent = Intent(this, ServicesActivity::class.java)
            startActivity(intent)
        }

        // Lógica para la pantalla de tareas (con mensaje temporal)
        binding.tasksButton.setOnClickListener {
            Toast.makeText(this, "Abriendo Tareas...", Toast.LENGTH_SHORT).show()
        }

        // Lógica para la pantalla de despensa (con mensaje temporal)
        binding.pantryButton.setOnClickListener {
            Toast.makeText(this, "Abriendo Despensa...", Toast.LENGTH_SHORT).show()
        }
    }
}