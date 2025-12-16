package com.example.connectio.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Tile(
    modifier: Modifier = Modifier,
    item: GameItem,
    color: Color = MaterialTheme.colorScheme.primary,
    width: Dp = 48.dp,
    height: Dp = 48.dp,
    text: String = "",
    onClick: () -> Unit = {},
    onDragStopped: (Int, Int) -> Unit = { rowMove, colMove -> }
) {
    val xOffsetPosition = remember { mutableStateOf(0f) }
    val yOffsetPosition = remember { mutableStateOf(0f) }
    var tileSize by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier
            .size(width = width, height = height)
            .background(color)
            .onSizeChanged { size ->
                tileSize = size
            },
        contentAlignment = Alignment.Center
    ) {
        if (item.type != MergeableType.EMPTY) {
            Box(
                Modifier
                    .fillMaxSize(0.8f)
                    .offset {
                        IntOffset(
                            xOffsetPosition.value.roundToInt(),
                            yOffsetPosition.value.roundToInt()
                        )
                    }
                    .clip(RoundedCornerShape(30))
                    .background(MaterialTheme.colorScheme.secondary)
                    .draggable2D(
                        state =
                            rememberDraggable2DState { delta ->
                                val newValueX = xOffsetPosition.value + delta.x
                                val newValueY = yOffsetPosition.value + delta.y

                                xOffsetPosition.value = newValueX
                                yOffsetPosition.value = newValueY
                            },
                        onDragStopped = {
                            if (tileSize.width > 0 && tileSize.height > 0) {
                                val rowMove = (xOffsetPosition.value / tileSize.width).roundToInt()
                                val colMove = (yOffsetPosition.value / tileSize.height).roundToInt()
                                xOffsetPosition.value = 0f
                                yOffsetPosition.value = 0f
                                onDragStopped(colMove, rowMove)
                            }
                        }
                    )
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            )
            {
                Text(item.display)
            }
        }
        Text(text = text)
    }
}

@Composable
@Preview
fun TilePreview() {
    Tile(item = MergeableItem(MergeableType.NUMBER, 3))
}