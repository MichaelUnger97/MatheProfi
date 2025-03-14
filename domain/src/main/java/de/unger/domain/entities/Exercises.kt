package de.unger.domain.entities

import java.time.LocalDateTime

data class Exercises(
    val kindOfExercises: KindOfExercise,
    val numberOfExercises: Int,
    val startTime: LocalDateTime,
    val easy: Boolean
)