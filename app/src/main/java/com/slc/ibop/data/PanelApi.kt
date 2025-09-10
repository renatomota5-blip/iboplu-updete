package com.slc.ibop.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.slc.ibop.AppConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

private val JSON = "application/json; charset=utf-8".toMediaType()
private val gson = Gson()
private val http = OkHttpClient.Builder()
    .connectTimeout(AppConfig.TIMEOUT_SEC, TimeUnit.SECONDS)
    .readTimeout(AppConfig.TIMEOUT_SEC, TimeUnit.SECONDS)
    .build()

// ======== MODELOS DE RESPOSTA (ajuste nomes conforme seu painel) ========
data class RegisterReq(val mac: String, val key: String, @SerializedName("app_version") val appVersion: String)
data class RegisterResp(val ok: Boolean, val activated: Boolean, val token: String? = null, val message: String? = null)

data class DnsItem(val name: String, val host: String, val port: Int? = null)
data class DnsResp(val ok: Boolean, val dns: List<DnsItem>? = null, val message: String? = null)

data class PlaylistResp(val ok: Boolean, @SerializedName("playlist_url") val playlistUrl: String? = null,
                        val playlist: String? = null, val message: String? = null)

// ===================== CHAMADAS ======================
object PanelApi {

    fun register(mac: String, key: String, appVersion: String): RegisterResp {
        val url = AppConfig.PANEL_API_BASE + AppConfig.EP_REGISTER
        val body = gson.toJson(RegisterReq(mac, key, appVersion))
        val req = Request.Builder()
            .url(url)
            .post(RequestBody.create(JSON, body))
            .build()
        http.newCall(req).execute().use { resp ->
            val text = resp.body?.string().orEmpty()
            if (!resp.isSuccessful) throw IllegalStateException("HTTP ${resp.code}: $text")
            return try { gson.fromJson(text, RegisterResp::class.java) }
            catch (_: Throwable) { throw IllegalStateException("Formato inválido do painel") }
        }
    }

    fun dnsList(): DnsResp {
        val url = AppConfig.PANEL_API_BASE + AppConfig.EP_DNS_LIST
        val req = Request.Builder().url(url).get().build()
        http.newCall(req).execute().use { resp ->
            val text = resp.body?.string().orEmpty()
            if (!resp.isSuccessful) throw IllegalStateException("HTTP ${resp.code}: $text")
            return try { gson.fromJson(text, DnsResp::class.java) }
            catch (_: Throwable) { throw IllegalStateException("Formato inválido (DNS)") }
        }
    }

    fun playlist(mac: String, key: String): PlaylistResp {
        val macQ = URLEncoder.encode(mac, "UTF-8")
        val keyQ = URLEncoder.encode(key, "UTF-8")
        val url = AppConfig.PANEL_API_BASE + AppConfig.EP_PLAYLIST + "?mac=$macQ&key=$keyQ"
        val req = Request.Builder().url(url).get().build()
        http.newCall(req).execute().use { resp ->
            val text = resp.body?.string().orEmpty()
            if (!resp.isSuccessful) throw IllegalStateException("HTTP ${resp.code}: $text")
            // O painel pode devolver { ok:true, playlist_url:"..." } ou { ok:true, playlist:"(conteudo M3U)" }
            return try { gson.fromJson(text, PlaylistResp::class.java) }
            catch (_: Throwable) { throw IllegalStateException("Formato inválido (playlist)") }
        }
    }
}
