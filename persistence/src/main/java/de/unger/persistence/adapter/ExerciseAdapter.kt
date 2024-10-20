package de.unger.persistence.adapter

import android.content.Context
import de.unger.domain.entities.Exercise
import de.unger.domain.port.ExerciseRepository
import de.unger.persistence.HerdenDatabase
import de.unger.persistence.entity.ExerciseEntity

class ExerciseAdapter(context: Context) : ExerciseRepository {
    private val exerciseDao = HerdenDatabase.DatabaseBuilder.getInstance(context).ExerciseDao()
    fun exerciseToExerciseEntity(exercise: Exercise): ExerciseEntity {
        return ExerciseEntity(
            exercise.firstArgument,
            exercise.secondArgument,
            exercise.kindOfExercise,
            exercise.result,
            exercise.index,
            exercise.result2
        )
    }

    fun exerciseFromExerciseEntity(exerciseEntity: ExerciseEntity): Exercise {
        return Exercise(
            exerciseEntity.firstArgument,
            exerciseEntity.secondArgument,
            exerciseEntity.kindOfExercise,
            exerciseEntity.result,
            exerciseEntity.index,
            exerciseEntity.result2
        )
    }

    override fun create(exercise: Exercise): Exercise {
        exerciseDao.insert(exerciseToExerciseEntity(exercise))
        return exercise
    }

    override fun findByIndex(index: Int): Exercise {
        return exerciseFromExerciseEntity(exerciseDao.findByIndex(index))
    }

    override fun numberOfExerciseEntities(): Int {
        return exerciseDao.numberOfExerciseEntities()
    }
}