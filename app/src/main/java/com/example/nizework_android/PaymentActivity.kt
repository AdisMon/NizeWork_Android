package com.example.nizework_android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- INICIO DE LA MODIFICACIÓN ---
        // Recibimos los datos que nos envió la pantalla anterior
        val serviceName = intent.getStringExtra("SERVICE_NAME")
        val transactionAmount = intent.getStringExtra("TRANSACTION_AMOUNT")

        // Usamos los datos recibidos. Por ejemplo, en el título.
        binding.headerTitle.text = "Pagar: $serviceName"
        // (Aquí podrías actualizar también los TextViews del monto, etc.)
        // --- FIN DE LA MODIFICACIÓN ---

        setupButtons()
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.payButton.setOnClickListener {
            Toast.makeText(this, "Procesando pago (Simulación)", Toast.LENGTH_SHORT).show()
        }
    }
}