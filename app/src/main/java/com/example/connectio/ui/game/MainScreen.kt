package com.example.connectio.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
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
    val expForNextLevel: Int = 100
)

@Composable
fun GameScreen() {
    var gameState by remember { mutableStateOf(GameState()) }
    fun onEnergyClick() {
        gameState = gameState.copy(energy = gameState.energy + 10)
    }

    fun levelUp() {
        gameState = gameState.copy(experience = gameState.experience - gameState.expForNextLevel)
        gameState = gameState.copy(level = gameState.level + 1)
        gameState = gameState.copy(expForNextLevel = gameState.expForNextLevel * 2)
    }

    fun onLevelClick() {
        gameState = gameState.copy(experience = gameState.experience + 10)
        if (gameState.experience >= gameState.expForNextLevel) {
            levelUp()
        }
    }

    MainScreen(
        onEnergyClick = { onEnergyClick() },
        onLevelClick = { onLevelClick() },
        gameState = gameState
    )
}

@Composable
fun MainScreen(onEnergyClick: () -> Unit, onLevelClick: () -> Unit, gameState: GameState) {
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
                rows = 9,
                cols = 7
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
                    id = -i,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                Text(text = "$i \uD83E\uDE99", modifier = Modifier.align(CenterHorizontally))
            }
        }
    }
}

@Composable
fun Board(modifier: Modifier = Modifier, rows: Int, cols: Int) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        for (row in 0..<rows) {
            Row {
                for (col in 0..<cols) {
                    val color = when ((row + col) % 2) {
                        0 -> MaterialTheme.colorScheme.primary
                        1 -> MaterialTheme.colorScheme.secondary
                        else
                            -> throw Exception("Wrong argument. Should be integers from 0 to 1")
                    }
                    Tile(id = row * cols + col, color = color, height = 52.dp, width = 52.dp)
                }
            }
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
            Tile(color = MaterialTheme.colorScheme.secondaryContainer, text = "\uD83D\uDCE6")
            Tile(color = MaterialTheme.colorScheme.secondaryContainer, text = "\uD83D\uDCE6")
            Tile(color = MaterialTheme.colorScheme.secondaryContainer, text = "\uD83D\uDCE6")
            Tile(color = MaterialTheme.colorScheme.secondaryContainer, text = "\uD83D\uDCE6")
        }
        Box(
            modifier = Modifier
                .weight(1F)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Tile(
                color = MaterialTheme.colorScheme.secondaryContainer,
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