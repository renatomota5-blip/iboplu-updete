package com.slc.ibop

object AppConfig {
    // 🔧 TROQUE AQUI PELO SEU DOMÍNIO DO PAINEL (com https:// ou http://)
    const val PANEL_API_BASE = "https://iboplus.motanetplay.top"

    // Endpoints padrões (ajuste os caminhos conforme seu painel)
    const val EP_REGISTER  = "/api/device/register"   // POST {mac,key,app_version}
    const val EP_DNS_LIST  = "/api/dns"               // GET  -> lista de DNS
    const val EP_PLAYLIST  = "/api/device/playlist"   // GET  ?mac=...&key=... -> m3u/m3u8 URL ou conteúdo
    const val TIMEOUT_SEC  = 30L
}
