package com.example.nizework_android.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    // 👇 CAMBIO 1: Usamos el MISMO nombre de archivo que usa tu compañera
    private val prefs: SharedPreferences = context.getSharedPreferences("NizeWorkPrefs", Context.MODE_PRIVATE)

    companion object {
        // 👇 CAMBIO 2: Usamos la MISMA llave (key) que usa ella (en mayúsculas)
        const val USER_ID = "USER_ID"
        const val USER_TOKEN = "USER_TOKEN" // Agregué el token por si lo ocupamos luego
    }

    // Guardar ID (Esta función quizás ya no se use si ella guarda manual, pero la dejamos por si acaso)
    fun saveUserId(id: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, id)
        editor.apply()
    }

    // Leer ID
    fun getUserId(): Int {
        // Devuelve -1 si no encuentra nada
        return prefs.getInt(USER_ID, -1)
    }

    // Leer Token (Extra, por si acaso)
    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    // Cerrar Sesión (Borra todo)
    fun logout() {
        prefs.edit().clear().apply()
    }
}