package com.example.connectio.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Tile(
    modifier: Modifier = Modifier,
    item: GameItem,
    color: Color = MaterialTheme.colorScheme.primary,
    width: Dp = 48.dp,
    height: Dp = 48.dp,
    text: String? = null,
    onClick: () -> Unit = {}
) {
    val item = item
    Box(
        modifier
            .size(width = width, height = height)
            .background(color)
            .clickable { onClick() }
    ) {
        Text(
            text = text ?: item.display,
            Modifier.align(Alignment.Center)
        )
    }
}