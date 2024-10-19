package de.unger.persistence.adapter

import android.content.Context
import de.unger.domain.entities.KindOfExercise
import de.unger.domain.entities.ResultOfExercises
import de.unger.domain.port.ResultOfExercisesRepository
import de.unger.persistence.HerdenDatabase
import de.unger.persistence.entity.ResultOfExercisesEntity

class ResultOfExercisesAdapter(context: Context) : ResultOfExercisesRepository {
    private val resultOfExercisesDao =
        HerdenDatabase.DatabaseBuilder.getInstance(context).ResultOfExercisesDao()

    private fun resultOfExercisesFromResultOfExercisesEntity(resultOfExercisesEntity: ResultOfExercisesEntity): ResultOfExercises {
        return ResultOfExercises(
            resultOfExercisesEntity.index,
            resultOfExercisesEntity.kindOfExercises,
            resultOfExercisesEntity.exercises,
            resultOfExercisesEntity.startTime,
            resultOfExercisesEntity.endTime,
            resultOfExercisesEntity.mistakes,
            resultOfExercisesEntity.name
        )
    }

    private fun resultOfExercisesToResultOfExercisesEntity(resultOfExercises: ResultOfExercises): ResultOfExercisesEntity {
        return ResultOfExercisesEntity(
            resultOfExercises.index,
            resultOfExercises.kindOfExercises,
            resultOfExercises.exercises,
            resultOfExercises.startTime,
            resultOfExercises.endTime,
            resultOfExercises.mistakes,
            resultOfExercises.name
        )
    }

    override fun create(resultOfExercises: ResultOfExercises): ResultOfExercises {
        resultOfExercisesDao.insert(resultOfExercisesToResultOfExercisesEntity(resultOfExercises))
        return resultOfExercises
    }

    override fun findResults(kindOfExercise: KindOfExercise): List<ResultOfExercises> {
        return resultOfExercisesDao.findResults(kindOfExercise)
            .map { resultOfExercisesFromResultOfExercisesEntity(it) }
    }

    override fun findLastResult(): ResultOfExercises? {
        val lastResult = resultOfExercisesDao.findLastResult()
        return if (lastResult == null) null else resultOfExercisesFromResultOfExercisesEntity(
            lastResult
        )
    }
}
