package com.example.nizework_android

import android.content.Intent // <-- SE AÑADIÓ ESTA LÍNEA
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityPantryBinding

class PantryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPantryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.addButton.setOnClickListener {
            val product = binding.productEditText.text.toString()
            val quantity = binding.quantityEditText.text.toString()

            if (product.isNotEmpty() && quantity.isNotEmpty()) {
                binding.productEditText.text.clear()
                binding.quantityEditText.text.clear()
            } else {
            }
        }

        binding.viewProductListButton.setOnClickListener {
            // Se necesita 'Intent' para esta acción
            val intent = Intent(this, PantryListActivity::class.java)
            startActivity(intent)
        }
    }
}