package de.unger.calculation.activity

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import de.unger.calculation.CalculationApp
import de.unger.calculation.ExceptionCatcher
import de.unger.calculation.databinding.ExerciseActivityLayoutBinding
import de.unger.domain.CalculationService
import de.unger.domain.entities.Exercise
import de.unger.domain.entities.ResultOfExercise

class MistakesActivity : AppCompatActivity() {

    private lateinit var binding: ExerciseActivityLayoutBinding
    private lateinit var exercise: Exercise
    private lateinit var exercises: List<Exercise>
    private lateinit var calculationApp: CalculationApp
    private lateinit var calculationService: CalculationService
    private lateinit var exceptionCatcher: ExceptionCatcher
    private var currentExerciseNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculationApp = application as CalculationApp
        calculationService = calculationApp.calculationService
        exceptionCatcher = ExceptionCatcher(calculationApp, this)
        binding = ExerciseActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uncasted = exceptionCatcher.catch {
            calculationService.findCurrentMistakes()
        }
        val exercises1 =
            if (uncasted is List<*>) uncasted else throw RuntimeException("not castable")
        if (exercises1.all { it is ResultOfExercise }) exercises =
            (exercises1 as List<ResultOfExercise>).map { it.exercise }
        if (exercises.isEmpty()) finish()
        else {
            exceptionCatcher.catch { calculationService.nextExercise(calculationApp.kindOfExercise!!) }
            exercise = exercises.first()
            initExercise(exercise)
        }
        binding.result.requestFocus()
        binding.result.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val uncasted = exceptionCatcher.catch {
                    calculationService.createResultOfExercise(
                        exercise,
                        binding.result.text.toString().toIntOrNull()
                    )
                }
                val result =
                    if (uncasted is ResultOfExercise) uncasted else throw RuntimeException("not castable")
                handleResult(result)
                exercise.result2?.let {
                    val uncasted1 = exceptionCatcher.catch {
                        calculationService.createResult2OfExercise(
                            exercise,
                            binding.result.text.toString().toIntOrNull()
                        )
                    }
                    val result1 =
                        if (uncasted1 is ResultOfExercise) uncasted1 else throw RuntimeException("not castable")
                    handleResult(result1)
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener super.onKeyUp(keyCode, event)
        }

    }

    private fun handleResult(result: ResultOfExercise) {
        if (result.correct) {
            binding.result.setBackgroundColor(Color.rgb(0, 150, 0))
            currentExerciseNumber++
            if (exercises.size < currentExerciseNumber) finish()
            else {
                initExercise(exercises[currentExerciseNumber])
            }
        } else binding.result.setBackgroundColor(Color.MAGENTA)
    }

    private fun initExercise(exercise: Exercise) {
        binding.sign.text = exercise.kindOfExercise.sign
        binding.first.text = exercise.firstArgument.toString()
        binding.second.text = exercise.secondArgument.toString()
    }

}