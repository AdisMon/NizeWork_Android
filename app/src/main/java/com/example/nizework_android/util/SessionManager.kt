package com.example.nizework_android.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("NizeWorkPrefs", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "USER_ID"
        const val USER_TOKEN = "USER_TOKEN"
    }

    fun saveUserId(id: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, id)
        editor.apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(USER_ID, -1)
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}