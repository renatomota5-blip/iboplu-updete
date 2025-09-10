package com.slc.ibop.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slc.ibop.data.DeviceIdentity
import com.slc.ibop.data.PanelApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen() {
    val ctx = androidx.compose.ui.platform.LocalContext.current
    val mac = remember { DeviceIdentity.getMac(ctx) }
    val key = remember { DeviceIdentity.getOrCreateAppKey(ctx) }

    var dnsText by remember { mutableStateOf<String?>(null) }
    var playlistText by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Ativado ✅", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("MAC: $mac")
        Text("Chave: $key")
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                error = null; dnsText = null
                scope.launch {
                    try {
                        val resp = withContext(Dispatchers.IO) { PanelApi.dnsList() }
                        dnsText = if (resp.ok) {
                            (resp.dns ?: emptyList()).joinToString("\n") {
                                val p = it.port?.let { ":$it" } ?: ""
                                "- ${it.name}: ${it.host}$p"
                            }.ifBlank { "Sem DNS cadastradas" }
                        } else resp.message ?: "Falha ao obter DNS"
                    } catch (t: Throwable) { error = t.message }
                }
            }) { Text("Obter DNS") }

            Button(onClick = {
                error = null; playlistText = null
                scope.launch {
                    try {
                        val resp = withContext(Dispatchers.IO) { PanelApi.playlist(mac, key) }
                        playlistText = when {
                            !resp.ok -> resp.message ?: "Falha ao obter playlist"
                            resp.playlistUrl != null -> "URL:\n${resp.playlistUrl}"
                            resp.playlist != null -> "CONTEÚDO M3U:\n${resp.playlist.take(500)}..."
                            else -> "Painel não retornou playlist"
                        }
                    } catch (t: Throwable) { error = t.message }
                }
            }) { Text("Obter Playlist") }
        }

        Spacer(Modifier.height(16.dp))
        if (dnsText != null) {
            Text("DNS:", style = MaterialTheme.typography.titleMedium)
            Text(dnsText!!)
            Spacer(Modifier.height(12.dp))
        }
        if (playlistText != null) {
            Text("Playlist:", style = MaterialTheme.typography.titleMedium)
            Text(playlistText!!)
            Spacer(Modifier.height(12.dp))
        }
        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }
    }
}
