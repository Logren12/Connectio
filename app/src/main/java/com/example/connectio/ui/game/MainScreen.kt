package com.example.connectio.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectio.ui.theme.ConnectioTheme

data class GameState(
    val energy: Int = 100,
    val coins: Int = 124,
    val experience: Int = 0,
    val level: Int = 1,
    val expForNextLevel: Int = 100,
    val cols: Int = 7,
    val rows: Int = 9,
    val board: List<GameItem> = List(cols * rows) { index ->
        if (index == 31) {
            Generator(MergeableType.NUMBER, 2)
        } else {
            MergeableItem(MergeableType.EMPTY)
        }
    }
)

@Composable
fun GameScreen() {
    var gameState by remember { mutableStateOf(GameState()) }
    fun levelUp() {
        gameState = gameState.copy(experience = gameState.experience - gameState.expForNextLevel)
        gameState = gameState.copy(level = gameState.level + 1)
        gameState = gameState.copy(expForNextLevel = gameState.expForNextLevel * 2)
    }

    fun onEnergyClick() {
        gameState = gameState.copy(energy = gameState.energy + 10)
    }

    fun onLevelClick() {
        gameState = gameState.copy(experience = gameState.experience + 10)
        if (gameState.experience >= gameState.expForNextLevel) {
            levelUp()
        }
    }

    fun onTileClick(gameItem: GameItem) {
        if (gameItem !is Generator) {
            return
        }
        if (gameState.energy < 1) {
            return
        }
        val emptyFields = gameState.board.filter { it.type == MergeableType.EMPTY }
        if (emptyFields.isEmpty()) {
            return
        }
        val targetField = emptyFields.random()
        val targetFieldIndex = gameState.board.indexOf(targetField)

        val newItem = gameItem.generateItem()
        val newBoard = gameState.board.toMutableList()
        newBoard[targetFieldIndex] = newItem
        gameState = gameState.copy(board = newBoard, energy = gameState.energy - 1)
    }

    MainScreen(
        onEnergyClick = { onEnergyClick() },
        onLevelClick = { onLevelClick() },
        onTileClick = { onTileClick(it) },
        gameState = gameState
    )
}

@Composable
fun MainScreen(
    onEnergyClick: () -> Unit,
    onLevelClick: () -> Unit,
    onTileClick: (GameItem) -> Unit,
    gameState: GameState
) {
    Scaffold(
        topBar = {
            ResourceBar(
                energy = gameState.energy,
                coins = gameState.coins,
                experience = gameState.experience,
                nextPlayerLevel = gameState.expForNextLevel,
                onEnergyClick = onEnergyClick,
                onLevelClick = onLevelClick,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.displayCutout)
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
            )
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(12.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            RequestsBar(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                5
            )
            Board(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                cols = gameState.cols,
                gameState = gameState,
                onTileClick = onTileClick
            )
        }
    }
}

@Composable
fun ResourceBar(
    energy: Int,
    experience: Int,
    coins: Int,
    nextPlayerLevel: Int,
    onEnergyClick: () -> Unit,
    onLevelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            onClick = onEnergyClick,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text("$energy⚡")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
                .fillMaxHeight()
        ) {
            LinearProgressIndicator(
                progress = { experience.toFloat() / nextPlayerLevel },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = MaterialTheme.colorScheme.primaryContainer,
                trackColor = MaterialTheme.colorScheme.background
            )
            Text(
                text = "$experience/$nextPlayerLevel exp"
            )
        }
        FilledTonalButton(
            onClick = onLevelClick,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text("\uD83E\uDE99$coins")
        }
    }
}

@Composable
fun RequestsBar(modifier: Modifier = Modifier, count: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..count) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Tile(
                    modifier = Modifier,
                    item = MergeableItem(MergeableType.NUMBER, 0),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                Text(text = "$i \uD83E\uDE99", modifier = Modifier.align(CenterHorizontally))
            }
        }
    }
}

@Composable
fun Board(
    modifier: Modifier = Modifier,
    cols: Int,
    gameState: GameState,
    onTileClick: (GameItem) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(cols),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(
            items = gameState.board,
            key = { item -> item.id }
        ) { item ->
            Tile(
                Modifier.aspectRatio(1f),
                item = item,
                color = MaterialTheme.colorScheme.primary,
                onClick = { onTileClick(item) }
            )
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .weight(3F),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Tile(
                item = MergeableItem(MergeableType.LETTER, 1),
                color = MaterialTheme.colorScheme.secondaryContainer,
                text = "\uD83D\uDCE6"
            )
            Tile(
                item = MergeableItem(MergeableType.NUMBER, 2),
                color = MaterialTheme.colorScheme.secondaryContainer,
                text = "\uD83D\uDCE6"
            )
            Tile(
                item = MergeableItem(MergeableType.EMPTY, 3),
                color = MaterialTheme.colorScheme.secondaryContainer,
                text = "\uD83D\uDCE6"
            )
            Tile(
                item = MergeableItem(MergeableType.LETTER, 0),
                color = MaterialTheme.colorScheme.secondaryContainer,
                text = "\uD83D\uDCE6"
            )
        }
        Box(
            modifier = Modifier
                .weight(1F)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Tile(
                color = MaterialTheme.colorScheme.secondaryContainer,
                item = MergeableItem(MergeableType.EMPTY, 0),
                width = 72.dp,
                height = 48.dp,
                text = "\uD83D\uDDD1\uFE0F"
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun GameScreenPreview() {
    ConnectioTheme(
        true
    ) {
        GameScreen()
    }
}