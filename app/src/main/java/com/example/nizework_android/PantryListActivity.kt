package com.example.nizework_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nizework_android.databinding.ActivityPantryListBinding

class PantryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPantryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        val pantryList = createSampleData()
        val adapter = PantryListAdapter(pantryList)
        binding.pantryRecyclerView.adapter = adapter
        binding.pantryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun createSampleData(): List<PantryItem> {
        // Creamos una lista de datos de prueba, como pediste
        return listOf(
            PantryItem("LECHE", "CANTIDAD: 4"),
            PantryItem("FRESAS", "CANTIDAD: 2KG"),
            PantryItem("GALLETAS", "CANTIDAD: 1"),
            PantryItem("HUEVOS", "CANTIDAD: 12"),
            PantryItem("PAN", "CANTIDAD: 1")
        )
    }
}