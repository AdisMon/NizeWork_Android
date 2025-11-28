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

    // Variables para guardar datos del servicio seleccionado
    private var serviceId: Int = -1
    private var montoInicial: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -----------------------------------------------------------
        // 1. RECIBIR DATOS QUE MANDASTE DESDE LA LISTA DE SERVICIOS
        // -----------------------------------------------------------
        serviceId = intent.getIntExtra("SERVICE_ID", -1)
        val montoTexto = intent.getStringExtra("TRANSACTION_AMOUNT") ?: "0"

        // Limpiamos el texto del monto (quitamos letras y símbolos)
        montoInicial = try {
            montoTexto.replace(Regex("[^0-9.]"), "").toDouble()
        } catch (e: Exception) { 0.0 }

        // -----------------------------------------------------------
        // 2. CONFIGURAR LA PANTALLA (UI)
        // -----------------------------------------------------------

        // Poner el monto sugerido en el campo editable (amountEditText)
        binding.amountEditText.setText(montoInicial.toString())

        // Generar un número de referencia aleatorio (Ej: REF-A1B2C3D4)
        val referencia = "REF-" + UUID.randomUUID().toString().substring(0, 8).uppercase()
        binding.txtReferencia.text = referencia

        // -----------------------------------------------------------
        // 3. AUTOCOMPLETAR DATOS DE LA TARJETA (DESDE MYSQL)
        // -----------------------------------------------------------
        cargarDatosTarjeta()

        // -----------------------------------------------------------
        // 4. CONFIGURAR BOTONES
        // -----------------------------------------------------------
        binding.payButton.setOnClickListener {
            realizarPago()
        }

        binding.backButton.setOnClickListener {
            finish() // Regresa a la pantalla anterior
        }
    }

    // Función para traer número y fecha desde la base de datos
    private fun cargarDatosTarjeta() {
        val sharedPref = getSharedPreferences("NizeWorkPrefs", MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)

        if (userId == -1) return

        // Creamos la instancia de la API
        val api = RetrofitClient.instance.create(ServiciosApiService::class.java)

        lifecycleScope.launch {
            try {
                // Llamamos al endpoint: /api/auth/tarjeta/{id}
                val response = api.getDatosTarjeta(userId)

                if (response.isSuccessful && response.body() != null) {
                    val tarjeta = response.body()!!

                    // Llenamos los campos automáticamente
                    binding.cardNumberEditText.setText(tarjeta.numero ?: "")
                    binding.cardholderNameEditText.setText(tarjeta.titular ?: "")

                    // Formateamos la fecha (MySQL: YYYY-MM-DD -> Tarjeta: MM/YY)
                    val fechaRaw = tarjeta.expiracion
                    if (!fechaRaw.isNullOrEmpty() && fechaRaw.length >= 7) {
                        try {
                            val year = fechaRaw.substring(2, 4) // "25"
                            val month = fechaRaw.substring(5, 7) // "09"
                            binding.expirationDateEditText.setText("$month/$year")
                        } catch (e: Exception) {
                            binding.expirationDateEditText.setText(fechaRaw)
                        }
                    }
                }
            } catch (e: Exception) {
                // Si falla la carga automática (error de red o JSON), no pasa nada.
                // El usuario simplemente verá los campos vacíos y podrá escribir.
            }
        }
    }

    // Función para procesar el pago en MongoDB
    private fun realizarPago() {
        // A. Validar que la tarjeta tenga datos
        if (binding.cardNumberEditText.text.isNullOrEmpty() || binding.ccvEditText.text.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor completa los datos de la tarjeta", Toast.LENGTH_SHORT).show()
            return
        }

        // B. Validar y Leer el MONTO EDITADO por el usuario
        // Aquí leemos lo que está escrito en el EditText, no lo que venía del Intent
        val montoString = binding.amountEditText.text.toString()
        val montoFinal = try {
            montoString.toDouble()
        } catch (e: Exception) {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que no sea cero o negativo
        if (montoFinal <= 0) {
            Toast.makeText(this, "El monto debe ser mayor a 0", Toast.LENGTH_SHORT).show()
            return
        }

        // C. Preparar datos para la API
        val sharedPref = getSharedPreferences("NizeWorkPrefs", MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)

        val api = RetrofitClient.instance.create(ServiciosApiService::class.java)

        // Creamos el objeto GastoRequest para enviar a Mongo
        val request = GastoRequest(
            idUsuario = userId,
            idServicio = serviceId,
            monto = montoFinal // Enviamos lo que el usuario decidió pagar
        )

        // D. Enviar a la API
        lifecycleScope.launch {
            try {
                // Bloquear botón para que no le piquen dos veces
                binding.payButton.isEnabled = false
                binding.payButton.text = "Procesando..."

                val response = api.registrarPago(request)

                if (response.isSuccessful) {
                    Toast.makeText(this@PaymentActivity, "¡Pago realizado con éxito!", Toast.LENGTH_LONG).show()

                    // E. Navegar al Home y limpiar el historial
                    val intent = Intent(this@PaymentActivity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // Si el servidor responde error (ej: 500)
                    Toast.makeText(this@PaymentActivity, "Error al procesar el pago", Toast.LENGTH_SHORT).show()
                    binding.payButton.isEnabled = true
                    binding.payButton.text = "PAGAR"
                }
            } catch (e: Exception) {
                // Si no hay internet o el servidor está apagado
                Toast.makeText(this@PaymentActivity, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
                binding.payButton.isEnabled = true
                binding.payButton.text = "PAGAR"
            }
        }
    }
}