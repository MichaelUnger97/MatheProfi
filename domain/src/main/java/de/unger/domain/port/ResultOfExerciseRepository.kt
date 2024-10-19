package de.unger.domain.port

import de.unger.domain.entities.ResultOfExercise
import java.time.LocalDateTime


interface ResultOfExerciseRepository {
    fun create(resultOfExercise: ResultOfExercise): ResultOfExercise
    fun findMistakes(): List<ResultOfExercise>
    fun findCurrentMistakes(
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): List<ResultOfExercise>
}
