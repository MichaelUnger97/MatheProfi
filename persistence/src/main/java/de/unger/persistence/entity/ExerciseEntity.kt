package de.unger.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.unger.domain.entities.KindOfExercise

@Entity
data class ExerciseEntity(
    val firstArgument: Int,
    val secondArgument: Int,
    val kindOfExercise: KindOfExercise,
    val result: Int,
    @PrimaryKey
    val index: Int,
    val result2: Int?
)