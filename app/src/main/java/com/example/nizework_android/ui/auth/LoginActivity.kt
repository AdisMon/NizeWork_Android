package com.example.nizework_android.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// IMPORTS NECESARIOS para el login con Retrofit
import com.example.nizework_android.data.api.AuthApiService
import com.example.nizework_android.data.model.Login
import com.example.nizework_android.data.model.ResponseLogin
import com.example.nizework_android.data.api.RetrofitClient
import com.example.nizework_android.ui.home.HomeActivity

import com.example.nizework_android.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authApiService: AuthApiService // Instancia para la llamada a la API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de Retrofit: Adaptado de tu línea Java
        // localNetworkAPI = ServiceRetrofit.getClient().create(LocalNetworkAPI.class);
        authApiService = RetrofitClient.instance.create(AuthApiService::class.java)

        // Configura los listeners de los botones
        setupLoginButton()

        Toast.makeText(this, "¡Bienvenido a NizeWork!", Toast.LENGTH_SHORT).show()
    }

    /**
     * Contiene la lógica de validación y la llamada a Retrofit (adaptada de tu onClick en Java).
     */
    private fun setupLoginButton() {
        // Asumiendo que el ID del botón es 'loginButton' o 'btnLogin'
        binding.btnLogin.setOnClickListener { // Usamos binding.btnLogin o binding.loginButton

            // Asumiendo IDs: txtEmail y txtPassword
            val usuario = binding.txtEmail.text.toString()
            val contra = binding.txtPassword.text.toString()

            // 1. Validación de campos (adaptada de tu 'if (usuario.isEmpty() || contra.isEmpty())')
            if (usuario.isEmpty() || contra.isEmpty()) {
                Toast.makeText(this, "Ingresa todos los campos para iniciar sesión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Sale del lambda
            }

            // 2. Creación del objeto Login (adaptada de tu 'login = new Login(); login.setUser(usuario);')
            val loginData = Login(user = usuario, password = contra)

            // 3. Llamada asíncrona a la API (adaptada de tu call.enqueue)
            val call = authApiService.setUser(loginData)
            call.enqueue(object : Callback<ResponseLogin> {

                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("RespuestaBien", response.message())

                        // Navegación exitosa (a HomeActivity, equivalente a tu MainActivity2.class)
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Manejo de errores 4xx (credenciales inválidas)
                        Toast.makeText(this@LoginActivity, "Error: Credenciales no válidas", Toast.LENGTH_LONG).show()
                        Log.d("LoginError", response.errorBody()?.string() ?: "Error de servidor")
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    // Fallo de red (no hay conexión)
                    Log.e("RespuestaMal", "Fallo de conexión: ${t.message}")
                    Toast.makeText(this@LoginActivity, "Error de red. Inténtalo de nuevo.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}