package com.example.nizework_android

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nizework_android.databinding.ActivityLoginBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- INICIO DE LA MODIFICACIÓN ---
        // Aplicamos el padding solo al contenedor del contenido principal
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainContent) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // --- FIN DE LA MODIFICACIÓN ---

        setupLoginButton()
        // Aquí puedes añadir la configuración para el botón de registro
        // setupRegisterButton()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val correctEmail = "test@test.com"
            val correctPassword = "1234"

            if (email == correctEmail && password == correctPassword) {
                showResultDialog("¡Éxito!", "Inicio de sesión correcto.")
            } else {
                showResultDialog("Error", "Credenciales incorrectas.")
            }
        }
    }

    // Si quieres añadir funcionalidad al botón de registrar
    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            // Lógica para ir a la pantalla de registro
            showResultDialog("Info", "Funcionalidad de registro no implementada.")
        }
    }

    private fun showResultDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}