package com.example.miniclipassessment.data

import com.example.miniclipassessment.model.FootballPlayer

const val POSITION_FORWARD = "forward"
const val POSITION_MIDFIELD = "midfield"
const val POSITION_DEFENDER = "defender"
const val POSITION_KEEPER = "keeper"

// Create 16 players for 4 teams featuring each position
fun createPlayers(): List<FootballPlayer> {
    return listOf(
        FootballPlayer(89, "Karim Benzema", POSITION_FORWARD),
        FootballPlayer(84, "Marco Verratti", POSITION_MIDFIELD),
        FootballPlayer(82, "Marquinhos", POSITION_DEFENDER),
        FootballPlayer(81, "Thibaut Courtois", POSITION_KEEPER),
        FootballPlayer(79, "Robert Lewandowski", POSITION_FORWARD),
        FootballPlayer(76, "Toni Kroos", POSITION_MIDFIELD),
        FootballPlayer(78, "Alphonso Davis", POSITION_DEFENDER),
        FootballPlayer(73, "Alisson Becker", POSITION_KEEPER),
        FootballPlayer(98, "Kylian Mbappe", POSITION_FORWARD),
        FootballPlayer(97, "Jude Bellingham", POSITION_MIDFIELD),
        FootballPlayer(96, "Virgil Van Dijk", POSITION_DEFENDER),
        FootballPlayer(99, "Wojciech Szsczesny", POSITION_KEEPER),
        FootballPlayer(68, "Brian Brobbey", POSITION_FORWARD),
        FootballPlayer(61, "Kenneth Taylor", POSITION_MIDFIELD),
        FootballPlayer(68, "Owen Wijndal", POSITION_DEFENDER),
        FootballPlayer(65, "Remco Pasveer", POSITION_KEEPER),
    )
}