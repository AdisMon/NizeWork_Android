package com.example.nizework_android.ui.services

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch

// --- IMPORTS CORRECTOS ---
import com.example.nizework_android.databinding.ActivityServicesBinding
import com.example.nizework_android.ui.payment.PaymentActivity
import com.example.nizework_android.ui.auth.LoginActivity
// Usamos ServicioItem (La nueva clase), NO 'Service'
import com.example.nizework_android.data.model.ServicioItem
import com.example.nizework_android.services.ServicesAdapter
import com.example.nizework_android.data.api.RetrofitClient
import com.example.nizework_android.data.api.ServiciosApiService
// -------------------------

class ServicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        cargarDatosDeApi()
        setupButtons()
    }

    private fun setupRecyclerView() {
        binding.servicesRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun cargarDatosDeApi() {
        val sharedPref = getSharedPreferences("NizeWorkPrefs", MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)

        if (userId == -1) {
            Toast.makeText(this, "Sesión inválida", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val serviceApi = RetrofitClient.instance.create(ServiciosApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = serviceApi.getServiciosPorUsuario(userId)

                if (response.isSuccessful && response.body() != null) {
                    val listaServicios = response.body()!!.servicios

                    val adapter = ServicesAdapter(listaServicios) { serviceItem ->
                        val intent = Intent(this@ServicesActivity, PaymentActivity::class.java)

                        intent.putExtra("SERVICE_ID", serviceItem.id)
                        intent.putExtra("SERVICE_NAME", serviceItem.nombre)
                        intent.putExtra("SERVICE_COMPANY", serviceItem.empresa)
                        intent.putExtra("TRANSACTION_AMOUNT", "150.00 LKR")

                        startActivity(intent)
                    }
                    binding.servicesRecyclerView.adapter = adapter

                } else {
                    Toast.makeText(this@ServicesActivity, "No se encontraron servicios", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ServicesActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}