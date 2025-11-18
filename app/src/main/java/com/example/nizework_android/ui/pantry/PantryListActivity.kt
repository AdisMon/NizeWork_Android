package com.example.nizework_android.ui.pantry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityPantryListBinding

class PantryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPantryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantryListBinding.inflate(layoutInflater)
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