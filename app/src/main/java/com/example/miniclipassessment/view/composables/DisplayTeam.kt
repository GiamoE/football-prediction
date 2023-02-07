package com.example.miniclipassessment.view.composables

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniclipassessment.R
import com.example.miniclipassessment.model.FootballTeam
import com.example.miniclipassessment.ui.theme.MiniclipassessmentTheme

@Composable
fun DisplayTeam(team: FootballTeam, ranking: Int?) {
    val context = LocalContext.current
    Divider(color = Color.White, thickness = dimensionResource(R.dimen.divider_width))
    Row(
        modifier = Modifier.height(dimensionResource(R.dimen.display_team_row_height)),
    ) {
        if (ranking != null) {
            Row(horizontalArrangement = Arrangement.Start) {
                TeamText(text = ranking.toString())
                TeamText(text = team.teamName)
                TeamText(text = "${stringResource(R.string.wins)}: ${team.wins}")
                TeamText(text = "${stringResource(R.string.goals)}: ${team.goals}")
            }
        } else {
            Text(
                color = Color.White,
                text = team.teamName,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    Toast.makeText(context, team.players.toString(), Toast.LENGTH_LONG
                    ).show()
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayTeamPreview() {
    MiniclipassessmentTheme {
        DisplayTeam(FootballTeam(), 1)
    }
}
