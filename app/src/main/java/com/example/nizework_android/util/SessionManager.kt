package com.example.nizework_android.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("nizework_session", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
    }

    // Guardar ID
    fun saveUserId(id: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, id)
        editor.apply()
    }

    // Leer ID (Devuelve -1 si no hay nadie logueado)
    fun getUserId(): Int {
        return prefs.getInt(USER_ID, -1)
    }

    // Cerrar Sesión
    fun logout() {
        prefs.edit().clear().apply()
    }
}