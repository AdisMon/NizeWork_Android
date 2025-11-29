package com.example.nizework_android.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.data.api.RetrofitClient
import com.example.nizework_android.data.model.DatosPersonales
import com.example.nizework_android.data.model.DatosUsuario
import com.example.nizework_android.data.model.ActualizarDatos
import com.example.nizework_android.data.model.ResponseUpdate
import com.example.nizework_android.databinding.ActivityProfileBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val authApiService = RetrofitClient.getAuthService()

    private var loadedPersonalData: DatosPersonales? = null
    private var loadedAccountData: DatosUsuario? = null
    private var currentUserId: Int = -1
    private var rawPassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserIdAndData()
        setupListeners()
    }
    private fun formatDbDateToUI(dbDate: String?): String {
        if (dbDate.isNullOrEmpty() || dbDate.length < 7) return dbDate ?: ""

        // Asumiendo formato YYYY-MM-DD
        return try {
            val year = dbDate.substring(2, 4)
            val month = dbDate.substring(5, 7)

            "$month/$year"
        } catch (e: Exception) {
            Log.e("LoadDate", "Error al formatear fecha de DB para UI: $dbDate, ${e.message}")
            dbDate
        }
    }


    private fun loadUserIdAndData() {
        val sharedPref = getSharedPreferences("NizeWorkPrefs", Context.MODE_PRIVATE)
        currentUserId = sharedPref.getInt("USER_ID", -1)
        val perfilJson = sharedPref.getString("USER_PROFILE_DATA", null)
        val cuentaJson = sharedPref.getString("USER_ACCOUNT_DATA", null)
        rawPassword = sharedPref.getString("USER_RAW_PASSWORD", null)

        if (currentUserId == -1 || perfilJson == null || cuentaJson == null || rawPassword == null) {
            Toast.makeText(this, "Error de sesión: Datos de perfil incompletos.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val gson = Gson()

        loadedPersonalData = try { gson.fromJson(perfilJson, DatosPersonales::class.java) } catch (e: Exception) { null }
        loadedAccountData = try { gson.fromJson(cuentaJson, DatosUsuario::class.java) } catch (e: Exception) { null }

        if (loadedPersonalData == null || loadedAccountData == null) {
            Toast.makeText(this, "Error al procesar datos de perfil/cuenta.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        loadedPersonalData?.let { data ->
            binding.txtNombre.setText(data.nombre)
            binding.txtApPaterno.setText(data.apellidoUno)
            binding.txtApMaterno.setText(data.apellidoDos)
            binding.txtEdad.setText(data.edad?.toString() ?: "")
            binding.txtTelefono.setText(data.telefono)
            binding.txtEmail.setText(data.email)
            binding.txtTipo.setText(loadedAccountData?.tipo ?: "")
            binding.txtNumTarjeta.setText(loadedAccountData?.numTarjeta ?: "")

            val formattedExpiration = formatDbDateToUI(loadedAccountData?.expiracion)
            binding.txtExpiracion.setText(formattedExpiration)
        }
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener { finish() }
        binding.btnGuardar.setOnClickListener { attemptUpdateProfile() }
    }

    private fun attemptUpdateProfile() {
        val initialPersonalData = loadedPersonalData
        val initialAccountData = loadedAccountData

        if (initialPersonalData == null || initialAccountData == null || currentUserId == -1) {
            Toast.makeText(this, "Datos no disponibles para actualizar.", Toast.LENGTH_LONG).show()
            return
        }

        val nombre = binding.txtNombre.text.toString().trim()
        val apellidoUno = binding.txtApPaterno.text.toString().trim()
        val apellidoDos = binding.txtApMaterno.text.toString().trim()
        val edadStr = binding.txtEdad.text.toString().trim()
        val telefono = binding.txtTelefono.text.toString().trim()
        val email = binding.txtEmail.text.toString().trim()
        val nuevaContraseniaIngresada = binding.txtPassword.text?.toString()?.trim() ?: ""
        val nuevoTipo = binding.txtTipo.text.toString().trim()
        val nuevoNumTarjeta = binding.txtNumTarjeta.text.toString().trim()
        val nuevaExpiracionRaw = binding.txtExpiracion.text.toString().trim()
        val edad = edadStr.toIntOrNull()
        if (nombre.isEmpty() || apellidoUno.isEmpty() || edad == null || telefono.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos principales.", Toast.LENGTH_SHORT).show()
            return
        }

        val passwordToSend = if (nuevaContraseniaIngresada.isEmpty()) {
            rawPassword ?: ""
        } else {
            nuevaContraseniaIngresada
        }

        val cleanedExpiration = nuevaExpiracionRaw.replace("[^0-9]".toRegex(), "")

        val nuevaExpiracionFormatted: String = if (cleanedExpiration.length == 4) {
            try {
                val month = cleanedExpiration.substring(0, 2)
                val year = cleanedExpiration.substring(2, 4).toInt()
                val fullYear = 2000 + year

                String.format("%04d-%s-01", fullYear, month)
            } catch (e: Exception) {
                Log.e("DateConversion", "Fallo de formato de fecha: ${e.message}")
                cleanedExpiration
            }
        } else {
            cleanedExpiration
        }

        val updateData = ActualizarDatos(
            nombre = nombre,
            apellidoUno = apellidoUno,
            apellidoDos = apellidoDos,
            edad = edad,
            telefono = telefono,
            email = email,

            contrasenia = passwordToSend,

            tipo = nuevoTipo.ifEmpty { initialAccountData.tipo ?: "" },
            numTarjeta = nuevoNumTarjeta.ifEmpty { initialAccountData.numTarjeta ?: "" },

            expiracion = nuevaExpiracionFormatted.ifEmpty { initialAccountData.expiracion ?: "" }
        )

        val gson = Gson()
        val jsonDebug = gson.toJson(updateData)
        Log.d("UpdateDebug", "JSON Enviado: $jsonDebug")

        val call = authApiService.updateUserData(currentUserId, updateData)
        call.enqueue(object : Callback<ResponseUpdate> {
            override fun onResponse(call: Call<ResponseUpdate>, response: Response<ResponseUpdate>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "✅ Datos actualizados con éxito", Toast.LENGTH_LONG).show()
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido."
                    Log.e("UpdateError", "Code: ${response.code()}, Msg: $errorMsg")
                    Toast.makeText(this@ProfileActivity, "Error al actualizar los datos: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseUpdate>, t: Throwable) {
                Log.e("UpdateFailure", "Fallo de conexión: ${t.message}")
                Toast.makeText(this@ProfileActivity, "Error en la conexión con el sistema.", Toast.LENGTH_LONG).show()
            }
        })
    }
}