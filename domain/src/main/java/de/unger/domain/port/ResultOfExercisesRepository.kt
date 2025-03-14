package de.unger.domain.port

import de.unger.domain.entities.KindOfExercise
import de.unger.domain.entities.ResultOfExercises


interface ResultOfExercisesRepository {
    fun create(resultOfExercises: ResultOfExercises): ResultOfExercises
    fun findResults(kindOfExercise: KindOfExercise, easy: Boolean): List<ResultOfExercises>
    fun findLastResult(): ResultOfExercises?
}
