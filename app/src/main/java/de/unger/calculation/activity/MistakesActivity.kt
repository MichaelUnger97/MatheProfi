package de.unger.calculation.activity

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import de.unger.calculation.CalculationApp
import de.unger.calculation.ExceptionCatcher
import de.unger.calculation.databinding.ExerciseActivityLayoutBinding
import de.unger.domain.CalculationService
import de.unger.domain.entities.Exercise
import de.unger.domain.entities.KindOfExercise
import de.unger.domain.entities.ResultOfExercise

class MistakesActivity : AppCompatActivity() {

    private lateinit var binding: ExerciseActivityLayoutBinding
    private lateinit var exercises: List<Exercise>
    private lateinit var calculationApp: CalculationApp
    private lateinit var calculationService: CalculationService
    private lateinit var exceptionCatcher: ExceptionCatcher
    private var currentExerciseNumber = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculationApp = application as CalculationApp
        calculationService = calculationApp.calculationService
        exceptionCatcher = ExceptionCatcher(calculationApp, this)
        binding = ExerciseActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uncasted = exceptionCatcher.catch {
            calculationService.findMistakes()
        }
        val exercises1 =
            if (uncasted is List<*>) uncasted else throw RuntimeException("not castable")
        if (exercises1.all { it is ResultOfExercise }) exercises =
            (exercises1 as List<ResultOfExercise>).map { it.exercise }
                .filter { it.kindOfExercise == calculationApp.kindOfExercise }
        if (exercises.isEmpty()) finish()
        else {
            initExercise(exercises.first())
        }
        binding.result.requestFocus()
        if (calculationApp.kindOfExercise == KindOfExercise.REMAINDER) {
            binding.remainder.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    val uncasted = exceptionCatcher.catch {
                        calculationService.createResult2OfExercise(
                            exercises[currentExerciseNumber],
                            binding.remainder.text.toString().toIntOrNull()
                        )
                    }
                    val result =
                        if (uncasted is ResultOfExercise) uncasted else throw RuntimeException("not castable")
                    if (result.correct) {
                        nextExercise()
                        binding.result.requestFocus()
                        binding.backgroundForResult.setBackgroundColor(Color.rgb(0, 150, 0))
                    } else {
                        binding.backgroundForResult.setBackgroundColor(Color.MAGENTA)

                    }
                    return@setOnKeyListener true
                }
                return@setOnKeyListener super.onKeyUp(keyCode, event)
            }
        } else {
            binding.remainder.visibility = View.GONE
        }
        binding.result.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val uncasted = exceptionCatcher.catch {
                    calculationService.createResultOfExercise(
                        exercises[currentExerciseNumber],
                        binding.result.text.toString().toIntOrNull()
                    )
                }
                val result =
                    if (uncasted is ResultOfExercise) uncasted else throw RuntimeException("not castable")
                if (result.correct) {
                    binding.backgroundForResult.setBackgroundColor(Color.rgb(0, 150, 0))
                    if (calculationApp.kindOfExercise == KindOfExercise.REMAINDER) {
                        binding.remainder.requestFocus()
                    } else {
                        nextExercise()
                    }
                } else binding.backgroundForResult.setBackgroundColor(Color.MAGENTA)

                return@setOnKeyListener true
            }
            return@setOnKeyListener super.onKeyUp(keyCode, event)
        }

    }

    private fun nextExercise() {
        currentExerciseNumber++
        if (currentExerciseNumber >= exercises.size) {
            finish()
        } else {
            initExercise(exercises[currentExerciseNumber])
        }

        binding.result.setText("")
        binding.remainder.setText("")
    }

    private fun initExercise(exercise: Exercise) {
        binding.sign.text = exercise.kindOfExercise.sign
        binding.first.text = exercise.firstArgument.toString()
        binding.second.text = exercise.secondArgument.toString()
    }

}