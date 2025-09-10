package com.slc.ibop.data

import android.content.Context

object Prefs {
    private const val NAME = "iboplus_prefs"
    private const val K_ACTIVATED = "activated"
    private const val K_TOKEN = "session_token"   // se painel devolver token/assinatura

    fun setActivated(context: Context, value: Boolean) {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(K_ACTIVATED, value).apply()
    }

    fun isActivated(context: Context): Boolean {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            .getBoolean(K_ACTIVATED, false)
    }

    fun saveToken(context: Context, token: String?) {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            .edit().putString(K_TOKEN, token).apply()
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            .getString(K_TOKEN, null)
    }
}
