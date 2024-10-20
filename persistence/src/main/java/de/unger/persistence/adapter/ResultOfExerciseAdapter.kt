package de.unger.persistence.adapter

import android.content.Context
import de.unger.domain.entities.ResultOfExercise
import de.unger.domain.port.ResultOfExerciseRepository
import de.unger.persistence.HerdenDatabase
import de.unger.persistence.entity.ResultOfExerciseEntity
import java.time.LocalDateTime

class ResultOfExerciseAdapter(context: Context) : ResultOfExerciseRepository {
    private val resultOfExerciseDao =
        HerdenDatabase.DatabaseBuilder.getInstance(context).ResultOfExerciseDao()
    private val exerciseAdapter = ExerciseAdapter(context)
    private fun resultOfExerciseFromResultOfExerciseEntity(resultOfExerciseEntity: ResultOfExerciseEntity): ResultOfExercise {
        return ResultOfExercise(
            resultOfExerciseEntity.resultDateTime,
            exerciseAdapter.findByIndex(resultOfExerciseEntity.exerciseIndex),
            resultOfExerciseEntity.correct
        )
    }

    private fun resultOfExerciseToResultOfExerciseEntity(resultOfExercise: ResultOfExercise): ResultOfExerciseEntity {
        return ResultOfExerciseEntity(
            resultOfExercise.resultDateTime,
            resultOfExercise.exercise.index,
            resultOfExercise.correct
        )
    }


    override fun create(resultOfExercise: ResultOfExercise): ResultOfExercise {
        resultOfExerciseDao.insert(resultOfExerciseToResultOfExerciseEntity(resultOfExercise))
        return resultOfExercise
    }

    override fun findMistakes(): List<ResultOfExercise> {
        return resultOfExerciseDao.findMistakes()
            .map { resultOfExerciseFromResultOfExerciseEntity(it) }
    }

    override fun findCurrentMistakes(
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): List<ResultOfExercise> {
        return resultOfExerciseDao.findCurrentMistakes(startTime, endTime)
            .map { resultOfExerciseFromResultOfExerciseEntity(it) }
    }


}
