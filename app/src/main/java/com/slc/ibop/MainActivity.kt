package com.slc.ibop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.slc.ibop.data.Prefs
import com.slc.ibop.ui.HomeScreen
import com.slc.ibop.ui.ProvisionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    var activated by remember { mutableStateOf(Prefs.isActivated(this)) }
                    if (activated) {
                        HomeScreen()
                    } else {
                        ProvisionScreen(onActivated = { activated = true })
                    }
                }
            }
        }
    }
}
