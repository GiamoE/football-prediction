package com.example.miniclipassessment.model

data class FootballTeam(
    val teamName: String = "default name",
    val players: List<FootballPlayer> = emptyList(),
    var points: Int = 0,
    var wins: Int = 0,
    var goals: Int = 0,
    var totalGoalsFor: Int = 0,
    var totalGoalsAgainst: Int = 0,
    var matches: MutableList<Match> = mutableListOf(),
)