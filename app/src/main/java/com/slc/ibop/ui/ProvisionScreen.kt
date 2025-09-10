package com.slc.ibop.ui

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.slc.ibop.data.DeviceIdentity
import com.slc.ibop.data.PanelApi
import com.slc.ibop.data.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProvisionScreen(
    onActivated: () -> Unit
) {
    val ctx = androidx.compose.ui.platform.LocalContext.current
    val mac = remember { DeviceIdentity.getMac(ctx) }
    var key by remember { mutableStateOf(DeviceIdentity.getOrCreateAppKey(ctx)) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ativação — IBO Plus", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("MAC do dispositivo:")
        Text(mac, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = key, onValueChange = { key = it },
            label = { Text("Chave do app") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))
        val scope = rememberCoroutineScope()
        Button(
            enabled = !loading && key.isNotBlank(),
            onClick = {
                loading = true; error = null
                scope.launch {
                    try {
                        val ver = "android-${Build.VERSION.SDK_INT}"
                        val resp = withContext(Dispatchers.IO) {
                            PanelApi.register(mac = mac, key = key, appVersion = ver)
                        }
                        if (resp.ok && resp.activated) {
                            Prefs.setActivated(ctx, true)
                            Prefs.saveToken(ctx, resp.token)
                            onActivated()
                        } else {
                            error = resp.message ?: "Ativação negada pelo painel"
                        }
                    } catch (t: Throwable) {
                        error = t.message ?: "Falha na ativação"
                    } finally {
                        loading = false
                    }
                }
            }
        ) {
            Text(if (loading) "Ativando..." else "Ativar")
        }
    }
}
