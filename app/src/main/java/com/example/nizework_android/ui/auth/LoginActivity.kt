package com.example.nizework_android.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.databinding.ActivityLoginBinding
import com.example.nizework_android.util.SessionManager
import com.example.nizework_android.data.api.AuthApiService
import com.example.nizework_android.data.api.RetrofitClient
import com.example.nizework_android.data.model.Login
import com.example.nizework_android.data.model.ResponseLogin
import com.example.nizework_android.ui.home.HomeActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authApiService: AuthApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Inicializar Retrofit
        authApiService = RetrofitClient.instance.create(AuthApiService::class.java)

        // 2. Configurar el botón
        setupLoginButton()
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {

            // A. Obtener datos de las cajas de texto
            val usuario = binding.txtEmail.text.toString().trim()
            val contra = binding.txtPassword.text.toString().trim()

            // B. Validar que no estén vacíos
            if (usuario.isEmpty() || contra.isEmpty()) {
                Toast.makeText(this, "Ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // C. Crear objeto Login para enviar (Solo usuario y password)
            val loginRequest = Login(user = usuario, password = contra)

            // D. Llamada a la API
            authApiService.setUser(loginRequest).enqueue(object : Callback<ResponseLogin> {

                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful && response.body() != null) {

                        // --- LÓGICA CORREGIDA PARA TU JSON ---
                        // Tu JSON devuelve una lista llamada "datos"
                        val listaDatos = response.body()?.datos

                        if (!listaDatos.isNullOrEmpty()) {
                            // Sacamos el primer usuario de la lista
                            val usuarioEncontrado = listaDatos[0]

                            // Verificamos el ID (IdUsuarioPK)
                            if (usuarioEncontrado.id != null) {

                                // 1. GUARDAR ID EN SESIÓN
                                val session = SessionManager(this@LoginActivity)
                                session.saveUserId(usuarioEncontrado.id)

                                Log.d("LOGIN_EXITO", "ID Guardado: ${usuarioEncontrado.id}")

                                // 2. NAVEGAR AL HOME
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                // Estas flags evitan que el usuario regrese al login con el botón 'atrás'
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()

                            } else {
                                Log.e("LOGIN_ERROR", "El usuario llegó sin ID (IdUsuarioPK es null)")
                                Toast.makeText(this@LoginActivity, "Error: Datos incompletos del servidor", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Log.e("LOGIN_ERROR", "La lista 'datos' está vacía")
                            Toast.makeText(this@LoginActivity, "Error desconocido al leer usuario", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        // Error 401 (Credenciales incorrectas)
                        Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Log.e("LOGIN_FAIL", "Error de conexión: ${t.message}")
                    Toast.makeText(this@LoginActivity, "Error de conexión. Revisa tu internet.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}