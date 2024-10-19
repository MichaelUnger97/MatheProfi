package de.unger.domain.port

import de.unger.domain.entities.Exercise

interface ExerciseRepository {
    fun create(exercise: Exercise): Exercise
    fun findByIndex(index: Int): Exercise
    fun numberOfExerciseEntities(): Int
}