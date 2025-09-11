package tech.jrplay.iboplus.ui
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class PlayerActivity : AppCompatActivity() {
  private var player: ExoPlayer? = null
  override fun onCreate(b: Bundle?) {
    super.onCreate(b)
    setContentView(FrameLayout(this).apply { id = android.R.id.content })
  }
  override fun onStart() {
    super.onStart()
    val url = intent.getStringExtra("stream") ?: return finish()
    player = ExoPlayer.Builder(this).build().also { p ->
      p.setMediaItem(MediaItem.fromUri(url)); p.prepare(); p.playWhenReady = true
      val pv = PlayerView(this); pv.player = p
      addContentView(pv, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }
  }
  override fun onStop() { player?.release(); player=null; super.onStop() }
}
