package com.example.miniclipassessment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val positionForward = "forward"
    private val positionMidfield = "midfield"
    private val positionDefender = "defender"
    private val positionKeeper = "keeper"

    fun initialSetup() {
        viewModelScope.launch {
            playerList = createPlayers()
            generateTeams()
        }
    }

    // Create 16 players for 4 teams featuring each position
    private fun createPlayers(): List<FootballPlayer> {
        return listOf(
            FootballPlayer(89, "Karim Benzema", positionForward),
            FootballPlayer(84, "Marco Verratti", positionMidfield),
            FootballPlayer(82, "Marquinhos", positionDefender),
            FootballPlayer(81, "Thibaut Courtois", positionKeeper),
            FootballPlayer(79, "Robert Lewandowski", positionForward),
            FootballPlayer(76, "Toni Kroos", positionMidfield),
            FootballPlayer(78, "Alphonso Davis", positionDefender),
            FootballPlayer(73, "Alisson Becker", positionKeeper),
            FootballPlayer(98, "Kylian Mbappe", positionForward),
            FootballPlayer(97, "Jude Bellingham", positionMidfield),
            FootballPlayer(96, "Virgil Van Dijk", positionDefender),
            FootballPlayer(99, "Wojciech Szsczesny", positionKeeper),
            FootballPlayer(68, "Brian Brobbey", positionForward),
            FootballPlayer(61, "Kenneth Taylor", positionMidfield),
            FootballPlayer(68, "Owen Wijndal", positionDefender),
            FootballPlayer(65, "Remco Pasveer", positionKeeper),
        )
    }

    // Fill each team with 4 players in every position of the field
    private fun generateTeams() {
        team1 = FootballTeam(playerList.slice(0..3), "Team A")
        team2 = FootballTeam(playerList.slice(4..7), "Team B")
        team3 = FootballTeam(playerList.slice(8..11), "Team C")
        team4 = FootballTeam(playerList.slice(12..15), "Team D")

        teamsList = listOf(team1, team2, team3, team4)
    }

    fun playAllMatches() {
        // Create a list so that there are only unique pairs of teams
        val teamsPlayed = mutableListOf(FootballTeam())
        for (homeTeam in teamsList) {
            for (awayTeam in teamsList) {
                if (homeTeam != awayTeam && awayTeam !in teamsPlayed) {
                    play(homeTeam, awayTeam)
                }
            }
            teamsPlayed.add(homeTeam)
        }
        generateResults()
    }

    private fun play(homeTeam: FootballTeam, awayTeam: FootballTeam) {
        // Split the teams into 4 positions that battle it out on the field
        val homeTeamAttack = homeTeam.players.first { it.position == positionForward }.strength
        val homeTeamMidfield = homeTeam.players.first { it.position == positionMidfield }.strength
        val homeTeamDefense = homeTeam.players.first { it.position == positionDefender }.strength
        val homeTeamKeeper = homeTeam.players.first { it.position == positionKeeper }.strength

        val awayTeamAttack = awayTeam.players.first { it.position == positionForward }.strength
        val awayTeamMidfield = awayTeam.players.first { it.position == positionMidfield }.strength
        val awayTeamDefense = awayTeam.players.first { it.position == positionDefender }.strength
        val awayTeamKeeper = awayTeam.players.first { it.position == positionKeeper }.strength

        var homeTeamGoals = 0
        var awayTeamGoals = 0

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
        while (homeTeamGoals < 3 && awayTeamGoals < 3) {
            simulate()
        }

        if (homeTeamGoals == 3) {
            homeTeam.wins++
            homeTeam.matches.add(
                Match(
                    awayTeamName = awayTeam.teamName,
                    goalsFor = homeTeamGoals,
                    goalsAgainst = awayTeamGoals,
                    won = true
                )
            )
        } else {
            awayTeam.wins++
            awayTeam.matches.add(
                Match(
                    awayTeamName = homeTeam.teamName,
                    goalsFor = awayTeamGoals,
                    goalsAgainst = homeTeamGoals,
                    won = true
                )
            )
        }

        Log.d(
            "Giamo",
            "Final score: ${homeTeam.teamName} : $homeTeamGoals " + "- $awayTeamGoals : ${awayTeam.teamName}"
        )
    }

    fun generateResults(): List<FootballTeam> {
        val sortedList = teamsList.sortedByDescending {
            it.wins
        }.sortedByDescending {
            it.goals
        }

        Log.d("Giamo", sortedList.size.toString() + " " + sortedList.toString())

//        // for each indexed, doesn't run 4 times (size of list) but instead 12 times resulting in index out of bounds.
//        sortedList.forEachIndexed { index, footballTeam ->
//            sortedList.sortedByDescending { footballTeam.matches[index].goalsFor }
//                .sortedByDescending {
//                    Log.d(
//                        "Giamo 2",
//                        "Index: $index  Goals: ${footballTeam.matches[index].goalsAgainst}  ${sortedList.size.toString()}"
//                    )
//                    footballTeam.matches[index].goalsAgainst
//                }
//                .forEachIndexed { _index, _footballTeam ->
//                    if (footballTeam.matches[index].awayTeamName == _footballTeam.teamName) {
//                        sortedList.sortedWith(compareByDescending {
//                            it.matches[index].won
//                        }, { _footballTeam.matches[_index].won })
//                    }
//                }
//        }
        return sortedList
    }

    fun reset() {
        for (team in teamsList) {
            team.goals = 0
            team.wins = 0
            team.matches.clear()
        }
    }
}
