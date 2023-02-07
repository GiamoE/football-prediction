package com.example.miniclipassessment.model

data class FootballTeam(
    val players: List<FootballPlayer> = emptyList(),
    val teamName: String = "default name",
    var wins: Int = 0,
    var goals: Int = 0,
    var matches: MutableList<Match> = mutableListOf(Match()),
)