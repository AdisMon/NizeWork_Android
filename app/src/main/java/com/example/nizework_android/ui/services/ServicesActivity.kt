package com.example.nizework_android.ui.services

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nizework_android.ui.payment.PaymentActivity
import com.example.nizework_android.R
import com.example.nizework_android.Service
import com.example.nizework_android.services.ServicesAdapter
import com.example.nizework_android.databinding.ActivityServicesBinding

class ServicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()
    }

    // --- INICIO DE LA MODIFICACIÓN ---
    private fun setupRecyclerView() {
        val serviceList = createSampleData()

        // Ahora, al crear el adaptador, le pasamos la lista Y la acción que debe ejecutar.
        val adapter = ServicesAdapter(serviceList) { service ->
            // Este bloque de código se ejecutará cuando se haga clic en una tarjeta.
            // 'service' es el objeto del servicio que fue presionado.

            val intent = Intent(this, PaymentActivity::class.java)

            // (Opcional pero recomendado) Enviamos datos a la siguiente pantalla.
            intent.putExtra("SERVICE_NAME", service.serviceName)
            intent.putExtra("TRANSACTION_AMOUNT", "150.00 LKR") // Puedes poner un valor de ejemplo

            startActivity(intent)
        }

        binding.servicesRecyclerView.adapter = adapter
        binding.servicesRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }
    // --- FIN DE LA MODIFICACIÓN ---

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun createSampleData(): List<Service> {
        return listOf(
            Service("básico", R.drawable.sideapalogo, "Servicio de agua", "SIDEAPA", "05/09/2025"),
            Service(
                "Entretenimiento",
                R.drawable.megacablelogo,
                "Servicio de cable",
                "MEGACABLE",
                "05/09/2025"
            ),
            Service(
                "Streaming",
                R.drawable.spotifylogo,
                "Servicio de música",
                "SPOTIFY",
                "05/09/2025"
            ),
            Service("básico", R.drawable.cfelogo, "Servicio de luz", "CFE", "05/09/2025"),
            Service(
                "Internet",
                R.drawable.telmexlogo,
                "Servicio de internet",
                "TELMEX",
                "10/09/2025"
            ),
            Service(
                "Telefonía",
                R.drawable.telcellogo,
                "Servicio de celular",
                "TELCEL",
                "15/09/2025"
            )
        )
    }
}