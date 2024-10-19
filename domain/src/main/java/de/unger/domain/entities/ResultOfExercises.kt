package de.unger.domain.entities

import java.time.LocalDateTime

data class ResultOfExercises(
    val index: Int,
    val kindOfExercises: KindOfExercise,
    val exercises: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val mistakes: Int,
    val name: String
)