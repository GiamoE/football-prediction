package com.example.miniclipassessment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniclipassessment.data.*
import com.example.miniclipassessment.model.FootballPlayer
import com.example.miniclipassessment.model.FootballTeam
import com.example.miniclipassessment.model.Match
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private var playerList = emptyList<FootballPlayer>()
    var teamsList = emptyList<FootballTeam>()
    private var team1: FootballTeam = FootballTeam()
    private var team2: FootballTeam = FootballTeam()
    private var team3: FootballTeam = FootballTeam()
    private var team4: FootballTeam = FootballTeam()

    fun initialSetup() {
        viewModelScope.launch {
            playerList = createPlayers()
            generateTeams()
        }
    }

    // Fill each team with 4 players in every position of the field
    private fun generateTeams() {
        team1 = FootballTeam("Team A", playerList.slice(0..3))
        team2 = FootballTeam("Team B", playerList.slice(4..7))
        team3 = FootballTeam("Team C", playerList.slice(8..11))
        team4 = FootballTeam("Team D", playerList.slice(12..15))

        teamsList = listOf(team1, team2, team3, team4)
    }

    fun playAllMatches() {
        // Create a list so that there are only unique pairs of teams
        val teamsPlayed = mutableListOf(FootballTeam())
        viewModelScope.launch {
            for (homeTeam in teamsList) {
                for (awayTeam in teamsList) {
                    if (homeTeam != awayTeam && awayTeam !in teamsPlayed) {
                        play(homeTeam, awayTeam)
                    }
                }
                teamsPlayed.add(homeTeam)
            }
        }
    }

    private fun play(homeTeam: FootballTeam, awayTeam: FootballTeam) {
        // Split the teams into 4 positions that battle it out on the field
        val homeTeamAttack = homeTeam.players.first { it.position == POSITION_FORWARD }.strength
        val homeTeamMidfield = homeTeam.players.first { it.position == POSITION_MIDFIELD }.strength
        val homeTeamDefense = homeTeam.players.first { it.position == POSITION_DEFENDER }.strength
        val homeTeamKeeper = homeTeam.players.first { it.position == POSITION_KEEPER }.strength

        val awayTeamAttack = awayTeam.players.first { it.position == POSITION_FORWARD }.strength
        val awayTeamMidfield = awayTeam.players.first { it.position == POSITION_MIDFIELD }.strength
        val awayTeamDefense = awayTeam.players.first { it.position == POSITION_DEFENDER }.strength
        val awayTeamKeeper = awayTeam.players.first { it.position == POSITION_KEEPER }.strength

        var homeTeamGoals = 0
        var awayTeamGoals = 0

        val winnerPoints = 3
        val drawPoints = 1

        fun simulate() {
            // Simulate the battle

            val randomMidfieldNumber = Random.nextInt(homeTeamMidfield + awayTeamMidfield)
            val randomHomeAttackingNumber = Random.nextInt(homeTeamAttack + awayTeamDefense)
            val randomHomeScoringNumber = Random.nextInt(homeTeamAttack + awayTeamKeeper)
            val randomAwayAttackingNumber = Random.nextInt(awayTeamAttack + homeTeamDefense)
            val randomAwayScoringNumber = Random.nextInt(awayTeamAttack + homeTeamKeeper)

            if (randomMidfieldNumber < homeTeamMidfield) {
                // continue home team to attack
                if (randomHomeAttackingNumber < homeTeamAttack) {
                    // continue home team to score
                    if (randomHomeScoringNumber < homeTeamAttack) {
                        homeTeamGoals++
                        homeTeam.goals++
                    } else {
                        simulate()
                    }
                } else {
                    simulate()
                }
            } else if (randomMidfieldNumber > homeTeamMidfield) {
                // continue away team to attack
                if (randomAwayAttackingNumber < awayTeamAttack) {
                    // continue away team to score
                    if (randomAwayScoringNumber < awayTeamAttack) {
                        awayTeamGoals++
                        awayTeam.goals++
                    } else {
                        simulate()
                    }
                } else {
                    simulate()
                }
            } else {
                /** If the random number equals the midfield strength we reset.
                 *  Otherwise one of the teams would gain a 1 point advantage over the away team
                 */
                simulate()
            }
        }

        // First to 3 goals wins
        val goalsNeededForWin = 3
        while (homeTeamGoals < goalsNeededForWin && awayTeamGoals < goalsNeededForWin) {
            simulate()
        }

        if (homeTeamGoals == goalsNeededForWin) {
            simulate()
            // win
            if (awayTeamGoals < goalsNeededForWin) {
                homeTeam.wins++
                homeTeam.points += winnerPoints
                homeTeam.matches.add(
                    Match(
                        awayTeamName = awayTeam.teamName,
                        goalsFor = homeTeamGoals,
                        goalsAgainst = awayTeamGoals,
                        matchResult = "won"
                    )
                )
                awayTeam.matches.add(
                    Match(
                        awayTeamName = homeTeam.teamName,
                        goalsFor = awayTeamGoals,
                        goalsAgainst = homeTeamGoals,
                        matchResult = "lost"
                    )
                )
                // draw
            } else {
                homeTeam.points += drawPoints
                homeTeam.matches.add(
                    Match(
                        awayTeamName = awayTeam.teamName,
                        goalsFor = homeTeamGoals,
                        goalsAgainst = awayTeamGoals,
                        matchResult = "draw"
                    )
                )
                awayTeam.points += drawPoints
                awayTeam.matches.add(
                    Match(
                        awayTeamName = homeTeam.teamName,
                        goalsFor = awayTeamGoals,
                        goalsAgainst = homeTeamGoals,
                        matchResult = "draw"
                    )
                )
            }
        } else {
            simulate()
            // win
            if (homeTeamGoals < goalsNeededForWin) {
                awayTeam.wins++
                awayTeam.points += winnerPoints
                awayTeam.matches.add(
                    Match(
                        awayTeamName = homeTeam.teamName,
                        goalsFor = awayTeamGoals,
                        goalsAgainst = homeTeamGoals,
                        matchResult = "won"
                    )
                )
                homeTeam.matches.add(
                    Match(
                        awayTeamName = awayTeam.teamName,
                        goalsFor = homeTeamGoals,
                        goalsAgainst = awayTeamGoals,
                        matchResult = "lost"
                    )
                )
                // draw
            } else {
                awayTeam.points += drawPoints
                awayTeam.matches.add(
                    Match(
                        awayTeamName = homeTeam.teamName,
                        goalsFor = awayTeamGoals,
                        goalsAgainst = homeTeamGoals,
                        matchResult = "draw"
                    )
                )
                homeTeam.points += drawPoints
                homeTeam.matches.add(
                    Match(
                        awayTeamName = awayTeam.teamName,
                        goalsFor = homeTeamGoals,
                        goalsAgainst = awayTeamGoals,
                        matchResult = "draw"
                    )
                )
            }
        }

        Log.d(
            "Giamo",
            "Final score: ${homeTeam.teamName} : $homeTeamGoals " + "- $awayTeamGoals : ${awayTeam.teamName}"
        )
    }

    fun generateResults(): List<FootballTeam> {
        val teamList = teamsList
        var indexTeams = 0
        var indexMatches = 0

        while (indexTeams < teamsList.size) {
            while (indexMatches < teamsList[0].matches.size) {
                teamList[indexTeams].totalGoalsFor += teamList[indexTeams].matches[indexMatches].goalsFor
                teamList[indexTeams].totalGoalsAgainst += teamList[indexTeams].matches[indexMatches].goalsAgainst
                indexMatches++
            }
            indexTeams++
        }

        val sortedList = teamList.sortedWith(compareByDescending<FootballTeam> { it.points }
                .thenByDescending { it.goals }
                .thenByDescending { it.totalGoalsAgainst }
                .thenBy { it.totalGoalsAgainst })

        Log.d("Giamo 2", "${sortedList[0]}")


        return sortedList
    }

    fun reset() {
        for (team in teamsList) {
            team.goals = 0
            team.wins = 0
            team.points = 0
            team.matches.clear()
        }
    }
}
