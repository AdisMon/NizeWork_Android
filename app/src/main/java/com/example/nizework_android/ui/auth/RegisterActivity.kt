package com.example.nizework_android.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    private fun setupButtons() {
        binding.registerButton.setOnClickListener {
            // Aquí puedes añadir lógica para leer los campos:
            // val fullName = binding.namesEditText.text.toString()
            // val password = binding.passwordEditText.text.toString()
            val intent = Intent(this, LogInActivity::class.java)
            // Estas flags limpian las actividades anteriores para que no se acumulen
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.loginLinkText.setOnClickListener {
            // Cierra esta actividad para volver al Login
            finish()
        }
    }
}