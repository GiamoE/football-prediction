package com.example.miniclipassessment.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniclipassessment.R
import com.example.miniclipassessment.model.FootballTeam
import com.example.miniclipassessment.ui.theme.MiniclipassessmentTheme

@Composable
fun DisplayTeams(teams: List<FootballTeam>, shouldSimulate: Boolean) {
    Column(
        modifier = Modifier
            .padding(
                start = dimensionResource(R.dimen.display_teams_padding_width),
                end = dimensionResource(R.dimen.display_teams_padding_width)
            )
            .width(dimensionResource(R.dimen.display_teams_column_width)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.display_teams_vertical_spacing),
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (shouldSimulate) {
            var numberRanking = 1
            teams.forEach { team ->
                DisplayTeam(team, numberRanking)
                numberRanking++
            }
        } else {
            teams.forEach { team ->
                DisplayTeam(team, null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayTeamsPreview() {
    MiniclipassessmentTheme {
        val teams = listOf(FootballTeam(), FootballTeam(), FootballTeam(), FootballTeam())
        DisplayTeams(teams, true)
    }
}
