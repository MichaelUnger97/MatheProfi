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
    fun startExercises(kindOfExercises: KindOfExercise, numberOfExercises: Int): Exercises {
        return Exercises(kindOfExercises, numberOfExercises, LocalDateTime.now())
    }

    fun nextExercise(kindOfExercise: KindOfExercise): Exercise {
        return exerciseRepository.create(getNextExercise(kindOfExercise))
    }

    private fun getNextExercise(kindOfExercise: KindOfExercise): Exercise {
        when (kindOfExercise) {
            KindOfExercise.PLUS -> {
                var first: Int
                var second: Int
                do {
                    first = (2..99).random()
                    second = (2..99).random()
                } while (first + second > 99)
                return Exercise(
                    first,
                    second,
                    kindOfExercise,
                    first + second,
                    exerciseRepository.numberOfExerciseEntities() + 1
                )

            }

            KindOfExercise.MINUS -> {
                var first: Int
                var second: Int
                do {
                    first = (3..99).random()
                    second = (2..98).random()
                } while (first - second < 1)
                return Exercise(
                    first,
                    second,
                    kindOfExercise,
                    first - second,
                    exerciseRepository.numberOfExerciseEntities() + 1
                )

            }

            KindOfExercise.MULTIPLY -> {

                val first = (2..9).random()
                val second = (2..9).random()
                return Exercise(
                    first,
                    second, kindOfExercise,
                    first * second,
                    exerciseRepository.numberOfExerciseEntities() + 1
                )
            }

            KindOfExercise.DIVIDE -> {
                val first = (2..9).random()
                val second = (2..9).random()
                return Exercise(
                    first * second,
                    first,
                    kindOfExercise,
                    second,
                    exerciseRepository.numberOfExerciseEntities() + 1
                )
            }

            KindOfExercise.REMAINDER -> {
                val first = (2..18).random()
                val second = (2..9).random()
                val third = (1 until first).random()
                return Exercise(
                    first * second + third,
                    first,
                    kindOfExercise,
                    second,
                    exerciseRepository.numberOfExerciseEntities() + 1,
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

    fun findResults(kindOfExercise: KindOfExercise) =
        resultOfExercisesRepository.findResults(kindOfExercise)

    fun createResultOfExercises(exercises: Exercises, name: String): ResultOfExercises {
        return resultOfExercisesRepository.create(
            ResultOfExercises(
                resultOfExercisesRepository.findResults(exercises.kindOfExercises).size + 1,
                exercises.kindOfExercises,
                exercises.numberOfExercises,
                exercises.startTime,
                LocalDateTime.now(),
                resultOfExerciseRepository.findCurrentMistakes(
                    exercises.startTime,
                    LocalDateTime.now()
                )
                    .count { resultOfExercise -> !resultOfExercise.correct },
                name
            )
        )
    }

    fun findMistakes(): List<ResultOfExercise> {
        return resultOfExerciseRepository.findMistakes()
    }

}