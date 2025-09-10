package com.slc.ibop.data

import android.content.Context
import android.provider.Settings
import java.security.MessageDigest
import java.util.UUID

object DeviceIdentity {

    // MAC estável por instalação (pseudo-MAC gerado a partir do ANDROID_ID)
    fun getMac(context: Context): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: UUID.randomUUID().toString()
        val digest = MessageDigest.getInstance("SHA-256").digest(androidId.toByteArray())
        // Formata como XX:XX:XX:XX:XX:XX (12 hex)
        val hex = digest.take(6).joinToString("") { "%02X".format(it) }
        return hex.chunked(2).joinToString(":")
    }

    // Chave do app gerada uma vez e guardada
    fun getOrCreateAppKey(context: Context): String {
        val prefs = context.getSharedPreferences("iboplus_prefs", Context.MODE_PRIVATE)
        val existing = prefs.getString("app_key", null)
        if (existing != null) return existing
        val newKey = generateKey()
        prefs.edit().putString("app_key", newKey).apply()
        return newKey
    }

    private fun generateKey(): String {
        // 20 chars hex em maiúsculo
        val rnd = UUID.randomUUID().toString().replace("-", "")
        return rnd.take(20).uppercase()
    }
}
