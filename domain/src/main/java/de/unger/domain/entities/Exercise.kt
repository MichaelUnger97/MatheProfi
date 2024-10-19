package de.unger.domain.entities

data class Exercise(
    val firstArgument: Int,
    val secondArgument: Int,
    val kindOfExercise: KindOfExercise,
    val result: Int,
    val index: Int
)