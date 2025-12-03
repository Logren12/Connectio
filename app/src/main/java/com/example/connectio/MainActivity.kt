package com.example.connectio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.connectio.ui.game.GameScreen
import com.example.connectio.ui.theme.ConnectioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //nie wiem jak i czemu ale działa (zmienia tło paska na przeźroczyste)
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(0x00000000))
        setContent {
            ConnectioTheme {
                GameScreen()
            }
        }

    }
}

@Preview(showBackground = false)
@Composable
fun GamePreview() {
    ConnectioTheme {
        GameScreen()
    }
}