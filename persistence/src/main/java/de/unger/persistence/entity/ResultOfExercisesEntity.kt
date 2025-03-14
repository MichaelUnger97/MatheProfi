package de.unger.persistence.entity

import androidx.room.Entity
import de.unger.domain.entities.KindOfExercise
import java.time.LocalDateTime

@Entity(primaryKeys = ["index", "kindOfExercises", "easy"])
data class ResultOfExercisesEntity(
    val index: Int,
    val kindOfExercises: KindOfExercise,
    val exercises: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val mistakes: Int,
    val name: String,
    val easy: Boolean
)