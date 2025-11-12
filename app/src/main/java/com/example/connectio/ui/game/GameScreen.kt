package com.example.connectio.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Composable
fun ResourceBar(
    energy: Int,
    experience: Int,
    coins: Int,
    nextPlayerLevel: Int,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedButton(onClick = {}) {
            Text("$energy⚡")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
                .height(24.dp)
        ){
            LinearProgressIndicator(
                progress = { experience.toFloat() / nextPlayerLevel },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = Color.Yellow,
                trackColor = Color.Magenta
            )
            Text(
                text = "$experience/$nextPlayerLevel exp"
            )
        }
        OutlinedButton(onClick = {}) {
            Text("$coins\uD83E\uDE99")
        }
    }
}
@Composable
fun RequestsBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column() {
            Text("Request 1")
            Text("DropItemSlot")
        }
        Column() {
            Text("Request 2")
            Text("DropItemSlot")
        }
        Column() {
            Text("Request 3")
            Text("DropItemSlot")
        }
    }
}
@Composable
fun Board(modifier: Modifier = Modifier, rows: Int, cols: Int){
    Box(modifier= modifier){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(0.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for(row in 0..< rows){
                Row {
                    for (col in 0..< cols) {
                        val color = when((row+col) % 2){
                            0 -> Color.Yellow
                            1 -> Color.Magenta
                            else
                                -> throw Exception("Wrong argument. Should be integers from 0 to 1")
                        }
                        Tile(id = row * cols + col, color = color, width = 48.dp,height = 48.dp)
                    }
                }
            }
        }
    }
}
@Composable
fun GameScreen(
) {
    var energy by remember { mutableIntStateOf(100) }
    var coins by remember { mutableIntStateOf(124) }
    var experience by remember { mutableIntStateOf(360) }
    var nextPlayerLevel by remember { mutableIntStateOf(999) }
    Scaffold(
        topBar = {
            ResourceBar(
                energy = energy,
                coins = coins,
                experience = experience,
                nextPlayerLevel = nextPlayerLevel,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .background(Color.Yellow)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ){
            RequestsBar(
                modifier = Modifier
                    .background(color = Color.Yellow)
                    .padding(12.dp)
                    .fillMaxWidth()
            )
            Board(
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 12.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .fillMaxHeight(),
                rows = 3,
                cols = 3

            )
        }
    }
}
@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(text = "Vault 1")
            Text(text = "Vault 2")
            Text(text = "Vault 3")
        }
        Text(text = "Trash placeholder")
    }
}
