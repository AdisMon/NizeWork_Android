package com.example.nizework_android.ui.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.UUID

// IMPORTS DE TUS CLASES (Asegúrate que coincidan con tus paquetes)
import com.example.nizework_android.databinding.ActivityPaymentBinding
import com.example.nizework_android.data.api.RetrofitClient
import com.example.nizework_android.data.api.ServiciosApiService
import com.example.nizework_android.data.model.GastoRequest
import com.example.nizework_android.ui.home.HomeActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private var serviceId: Int = -1
    private var montoInicial: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        serviceId = intent.getIntExtra("SERVICE_ID", -1)
        val montoTexto = intent.getStringExtra("TRANSACTION_AMOUNT") ?: "0"
        montoInicial = try {
            montoTexto.replace(Regex("[^0-9.]"), "").toDouble()
        } catch (e: Exception) { 0.0 }
        binding.amountEditText.setText(montoInicial.toString())
        val referencia = "REF-" + UUID.randomUUID().toString().substring(0, 8).uppercase()

        binding.txtReferencia.text = referencia
        cargarDatosTarjeta()

        binding.payButton.setOnClickListener {
            realizarPago()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun cargarDatosTarjeta() {
        val sharedPref = getSharedPreferences("NizeWorkPrefs", MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)

        if (userId == -1) return

        val api = RetrofitClient.instance.create(ServiciosApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = api.getDatosTarjeta(userId)

                if (response.isSuccessful && response.body() != null) {
                    val tarjeta = response.body()!!

                    binding.cardNumberEditText.setText(tarjeta.numero ?: "")
                    binding.cardholderNameEditText.setText(tarjeta.titular ?: "")

                    val fechaRaw = tarjeta.expiracion
                    if (!fechaRaw.isNullOrEmpty() && fechaRaw.length >= 7) {
                        try {
                            val year = fechaRaw.substring(2, 4)
                            val month = fechaRaw.substring(5, 7)
                            binding.expirationDateEditText.setText("$month/$year")
                        } catch (e: Exception) {
                            binding.expirationDateEditText.setText(fechaRaw)
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun realizarPago() {
        if (binding.cardNumberEditText.text.isNullOrEmpty() || binding.ccvEditText.text.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor completa los datos de la tarjeta", Toast.LENGTH_SHORT).show()
            return
        }

        val montoString = binding.amountEditText.text.toString()
        val montoFinal = try {
            montoString.toDouble()
        } catch (e: Exception) {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
            return
        }

        if (montoFinal <= 0) {
            Toast.makeText(this, "El monto debe ser mayor a 0", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("NizeWorkPrefs", MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)
        val api = RetrofitClient.instance.create(ServiciosApiService::class.java)
        val request = GastoRequest(
            idUsuario = userId,
            idServicio = serviceId,
            monto = montoFinal
        )

        lifecycleScope.launch {
            try {
                binding.payButton.isEnabled = false
                binding.payButton.text = "Procesando..."

                val response = api.registrarPago(request)

                if (response.isSuccessful) {
                    Toast.makeText(this@PaymentActivity, "¡Pago realizado con éxito!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@PaymentActivity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@PaymentActivity, "Error al procesar el pago", Toast.LENGTH_SHORT).show()
                    binding.payButton.isEnabled = true
                    binding.payButton.text = "PAGAR"
                }
            } catch (e: Exception) {
                Toast.makeText(this@PaymentActivity, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
                binding.payButton.isEnabled = true
                binding.payButton.text = "PAGAR"
            }
        }
    }
}