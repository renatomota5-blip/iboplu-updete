package tech.jrplay.iboplus.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(b: Bundle?) {
    super.onCreate(b)
    val wrap = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(24,24,24,24) }
    fun open(url: String) =
      startActivity(Intent(this, WebPageActivity::class.java).putExtra("url", url))

    wrap.addView(Button(this).apply { text = "Banner (apiautoads.php)"; setOnClickListener { open(Constants.ADS1) } })
    wrap.addView(Button(this).apply { text = "Backdrop 2 (apiautoads2.php)"; setOnClickListener { open(Constants.ADS2) } })
    wrap.addView(Button(this).apply { text = "QR (qr.php)"; setOnClickListener { open(Constants.QR) } })
    wrap.addView(Button(this).apply { text = "Contato (contato.php)"; setOnClickListener { open(Constants.CONTATO) } })
    wrap.addView(Button(this).apply {
      text = "Política de Privacidade (embutida)"
      setOnClickListener { startActivity(Intent(this@MainActivity, LegalActivity::class.java).putExtra("page","privacy")) }
    })
    wrap.addView(Button(this).apply {
      text = "Termos (embutido)"
      setOnClickListener { startActivity(Intent(this@MainActivity, LegalActivity::class.java).putExtra("page","terms")) }
    })
    setContentView(wrap)
  }
}
