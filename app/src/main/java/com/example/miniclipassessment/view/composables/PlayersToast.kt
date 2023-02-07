package com.example.miniclipassessment.view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miniclipassessment.model.FootballPlayer

@Composable
fun PlayersToast(players: List<FootballPlayer>) {
    Column(modifier = Modifier.height(600.dp)) {
        players.forEach {
            Text("Player: ${it.name} strength: ${it.strength}")
        }
    }
}