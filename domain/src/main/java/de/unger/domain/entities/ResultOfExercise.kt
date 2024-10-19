package de.unger.domain.entities

import java.time.LocalDateTime

data class ResultOfExercise(
    val resultDateTime: LocalDateTime,
    val exercise: Exercise,
    val correct: Boolean
)