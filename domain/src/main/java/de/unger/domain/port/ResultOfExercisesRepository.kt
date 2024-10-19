package de.unger.domain.port

import de.unger.domain.entities.KindOfExercise
import de.unger.domain.entities.ResultOfExercises


interface ResultOfExercisesRepository {
    fun create(resultOfExercises: ResultOfExercises): ResultOfExercises
    fun findResults(kindOfExercise: KindOfExercise): List<ResultOfExercises>
    fun findLastResult(): ResultOfExercises?
}
