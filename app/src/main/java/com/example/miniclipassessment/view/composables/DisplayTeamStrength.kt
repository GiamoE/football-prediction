package com.example.miniclipassessment.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniclipassessment.R
import com.example.miniclipassessment.model.FootballPlayer
import com.example.miniclipassessment.model.FootballTeam

@Composable
fun DisplayTeamStrength(players: List<FootballPlayer>) {
    Row(Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End){
        var totalStrength = 0
        players.forEach {
            totalStrength += it.strength
        }
        Text(color = Color.White, text = "${stringResource(R.string.team_strength_text)}: $totalStrength")
    }
}

@Preview
@Composable
fun PreviewDisplayTeamStrength() {
    DisplayTeamStrength(FootballTeam().players)
}