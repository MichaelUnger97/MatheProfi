package de.unger.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class ResultOfExerciseEntity(
    @PrimaryKey
    val resultDateTime: LocalDateTime,
    val exerciseIndex: Int,
    val correct: Boolean
)