package com.example.connectio.ui.game

import androidx.compose.foundation.background
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
import com.squareup.kotlinpoet.NUMBER

@Composable
fun Tile(
    modifier: Modifier = Modifier,
    item: MergeableItem,
    color: Color = MaterialTheme.colorScheme.primary,
    width: Dp = 48.dp,
    height: Dp = 48.dp,
    text: String = ""
) {
    Box(
        modifier
            .size(width = width, height = height)
            .background(color)
    ) {
        Text(
            text = item.display,
            Modifier.align(Alignment.Center)
        )
    }
}