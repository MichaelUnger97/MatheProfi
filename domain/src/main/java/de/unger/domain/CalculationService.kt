package de.unger.domain

import de.unger.domain.entities.Exercise
import de.unger.domain.entities.Exercises
import de.unger.domain.entities.KindOfExercise
import de.unger.domain.entities.ResultOfExercise
import de.unger.domain.entities.ResultOfExercises
import de.unger.domain.port.ExerciseRepository
import de.unger.domain.port.ResultOfExerciseRepository
import de.unger.domain.port.ResultOfExercisesRepository
import java.time.LocalDateTime


open class CalculationService(
    private val resultOfExerciseRepository: ResultOfExerciseRepository,
    private val resultOfExercisesRepository: ResultOfExercisesRepository,
    private val exerciseRepository: ExerciseRepository
) {
    fun startExercises(
        kindOfExercises: KindOfExercise,
        numberOfExercises: Int,
        easy: Boolean
    ): Exercises {
        return Exercises(kindOfExercises, numberOfExercises, LocalDateTime.now(), easy)
    }

    fun nextExercise(kindOfExercise: KindOfExercise, easy: Boolean): Exercise {
        return exerciseRepository.create(getNextExercise(kindOfExercise, easy))
    }

    private fun getNextExercise(kindOfExercise: KindOfExercise, easy: Boolean): Exercise {
        when (kindOfExercise) {
            KindOfExercise.PLUS -> {
                var first: Int
                var second: Int
                val range = if (easy) 2..99 else 101..999
                val result = if (easy) 99 else 999
                do {
                    first = range.random()
                    second = range.random()
                } while (first + second > result)
                return Exercise(
                    first,
                    second,
                    kindOfExercise,
                    first + second,
                    exerciseRepository.numberOfExerciseEntities() + 1,
                    easy
                )

            }

            KindOfExercise.MINUS -> {
                var first: Int
                var second: Int
                val range = if (easy) 2..99 else 101..999
                val result = if (easy) 1 else 101
                do {
                    first = range.random()
                    second = range.random()
                } while (first - second < result)
                return Exercise(
                    first,
                    second,
                    kindOfExercise,
                    first - second,
                    exerciseRepository.numberOfExerciseEntities() + 1,
                    easy
                )

            }

            KindOfExercise.MULTIPLY -> {

                val range = if (easy) 2..9 else 11..19
                val first = range.random()
                val second = range.random()
                return Exercise(
                    first,
                    second, kindOfExercise,
                    first * second,
                    exerciseRepository.numberOfExerciseEntities() + 1,
                    easy
                )
            }

            KindOfExercise.DIVIDE -> {
                val range = if (easy) 2..9 else 11..19
                val first = range.random()
                val second = range.random()
                return Exercise(
                    first * second,
                    first,
                    kindOfExercise,
                    second,
                    exerciseRepository.numberOfExerciseEntities() + 1,
                    easy
                )
            }

            KindOfExercise.REMAINDER -> {
                val range = if (easy) 2..9 else 11..19
                val first = range.random()
                val second = range.random()
                val third = (1 until first).random()
                return Exercise(
                    first * second + third,
                    first,
                    kindOfExercise,
                    second,
                    exerciseRepository.numberOfExerciseEntities() + 1,
                    easy,
                    third
                )
            }
        }
    }

    fun createResultOfExercise(exercise: Exercise, result: Int?): ResultOfExercise {
        if (result == null) throw RuntimeException("Kein Ergebnis Eingegeben")
        return resultOfExerciseRepository.create(
            ResultOfExercise(
                LocalDateTime.now(),
                exercise,
                result == exercise.result
            )
        )
    }

    fun createResult2OfExercise(exercise: Exercise, result: Int?): ResultOfExercise {
        if (result == null) throw RuntimeException("Kein Ergebnis Eingegeben")
        return resultOfExerciseRepository.create(
            ResultOfExercise(
                LocalDateTime.now(),
                exercise,
                result == exercise.result2
            )
        )
    }

    fun findResults(kindOfExercise: KindOfExercise, easy: Boolean) =
        resultOfExercisesRepository.findResults(kindOfExercise, easy)

    fun createResultOfExercises(exercises: Exercises, name: String): ResultOfExercises {
        return resultOfExercisesRepository.create(
            ResultOfExercises(
                resultOfExercisesRepository.findResults(
                    exercises.kindOfExercises,
                    exercises.easy
                ).size + 1,
                exercises.kindOfExercises,
                exercises.numberOfExercises,
                exercises.startTime,
                LocalDateTime.now(),
                resultOfExerciseRepository.findCurrentMistakes(
                    exercises.startTime,
                    LocalDateTime.now()
                )
                    .count { resultOfExercise -> !resultOfExercise.correct },
                name,
                exercises.easy
            )
        )
    }

    fun findMistakes(): List<ResultOfExercise> {
        return resultOfExerciseRepository.findMistakes()
    }

}