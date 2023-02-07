package com.example.miniclipassessment.model

data class Match(
    val awayTeamName: String = "default name",
    var goalsFor: Int = 0,
    var goalsAgainst: Int = 0,
    var won: Boolean = false
)