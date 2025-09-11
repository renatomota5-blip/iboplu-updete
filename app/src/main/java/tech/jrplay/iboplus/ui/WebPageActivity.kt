package tech.jrplay.iboplus.ui
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class WebPageActivity : AppCompatActivity() {
  override fun onCreate(b: Bundle?) {
    super.onCreate(b)
    val url = intent.getStringExtra("url") ?: return finish()
    val wv = WebView(this)
    setContentView(wv)
    wv.settings.javaScriptEnabled = true
    wv.settings.domStorageEnabled = true
    wv.settings.mediaPlaybackRequiresUserGesture = false
    wv.settings.safeBrowsingEnabled = true
    wv.loadUrl("$url?cb=${System.currentTimeMillis()}")
  }
}
