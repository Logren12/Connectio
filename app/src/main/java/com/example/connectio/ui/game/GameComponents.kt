package com.example.connectio.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
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
    id: Int,
    color:Color,
    width: Dp,
    height: Dp
){
    Box(Modifier
        .size(width = width, height = height)
        .background(color)){
        Text("$id", Modifier.align(Alignment.Center))
    }
}