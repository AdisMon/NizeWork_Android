package com.example.nizework_android.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.nizework_android.data.api.RetrofitClient
import com.example.nizework_android.data.model.Login
import com.example.nizework_android.data.model.ResponseLogin
import com.example.nizework_android.databinding.ActivityLoginBinding
import com.example.nizework_android.ui.home.HomeActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authApiService = RetrofitClient.getAuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniciarSesion()
    }


    private fun iniciarSesion() {
        binding.btnLogin.setOnClickListener {

            val usuario = binding.txtEmail.text.toString()
            val contra = binding.txtPassword.text.toString()

            if (usuario.isEmpty() || contra.isEmpty()) {
                Toast.makeText(this, "Ingresa todos los campos para iniciar sesión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginData = Login(user = usuario, password = contra)

            val call = authApiService.setUser(loginData)
            call.enqueue(object : Callback<ResponseLogin> {

                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful && response.body() != null) {

                        val apiResponse = response.body()!!

                        if (!apiResponse.datos.isNullOrEmpty() && !apiResponse.datosPersonales.isNullOrEmpty()) {

                            val usuarioEncontrado = apiResponse.datos[0]
                            val datosPerfil = apiResponse.datosPersonales[0]

                            val sharedPref = getSharedPreferences("NizeWorkPrefs", Context.MODE_PRIVATE)
                            val gson = Gson()
                            val perfilJson = gson.toJson(datosPerfil)
                            val cuentaJson = gson.toJson(usuarioEncontrado)

                            with(sharedPref.edit()) {
                                putInt("USER_ID", usuarioEncontrado.idUsuario)
                                putString("USER_TOKEN", apiResponse.token)
                                putString("USER_PROFILE_DATA", perfilJson)
                                putString("USER_ACCOUNT_DATA", cuentaJson)
                                putString("USER_RAW_PASSWORD", apiResponse.contraIngresada)
                                apply()
                            }

                            Log.d("LoginExito", "Usuario ID: ${usuarioEncontrado.idUsuario} y perfiles guardados.")

                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(this@LoginActivity, "Error: Perfil no encontrado", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        val errorMsg = try {
                            response.errorBody()?.string()
                        } catch (e: Exception) { "Error de servidor" }
                        Log.d("LoginError", errorMsg ?: "")
                        Toast.makeText(this@LoginActivity, "Credenciales no válidas", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Log.e("RespuestaMal", "Fallo de conexión: ${t.message}")
                    Toast.makeText(this@LoginActivity, "Error de red. Verifica tu conexión.", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}