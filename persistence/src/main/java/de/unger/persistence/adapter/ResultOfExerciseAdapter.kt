package de.unger.persistence.adapter

import android.content.Context
import de.unger.domain.entities.Exercise
import de.unger.domain.entities.ResultOfExercise
import de.unger.domain.port.ResultOfExerciseRepository
import de.unger.persistence.HerdenDatabase
import de.unger.persistence.entity.ExerciseEntity
import de.unger.persistence.entity.ResultOfExerciseEntity
import java.time.LocalDateTime

class ResultOfExerciseAdapter(context: Context) : ResultOfExerciseRepository {
    private val resultOfExerciseDao =
        HerdenDatabase.DatabaseBuilder.getInstance(context).ResultOfExerciseDao()
    private val exerciseDao = HerdenDatabase.DatabaseBuilder.getInstance(context).ExerciseDao()
    private fun resultOfExerciseFromResultOfExerciseEntity(resultOfExerciseEntity: ResultOfExerciseEntity): ResultOfExercise {
        return ResultOfExercise(
            resultOfExerciseEntity.resultDateTime,
            exerciseFromExerciseEntity(exerciseDao.findByIndex(resultOfExerciseEntity.exerciseIndex)),
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

    private fun exerciseFromExerciseEntity(exerciseEntity: ExerciseEntity): Exercise {
        return Exercise(
            exerciseEntity.firstArgument,
            exerciseEntity.secondArgument,
            exerciseEntity.kindOfExercise,
            exerciseEntity.result,
            exerciseEntity.index
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
