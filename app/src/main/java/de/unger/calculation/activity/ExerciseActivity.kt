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
import de.unger.domain.entities.Exercises
import de.unger.domain.entities.ResultOfExercise

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ExerciseActivityLayoutBinding
    private lateinit var exercise: Exercise
    private lateinit var exercises: Exercises
    private lateinit var calculationApp: CalculationApp
    private lateinit var calculationService: CalculationService
    private lateinit var exceptionCatcher: ExceptionCatcher
    private var currentNumberOfExercise = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculationApp = application as CalculationApp
        calculationService = calculationApp.calculationService
        exceptionCatcher = ExceptionCatcher(calculationApp, this)
        binding = ExerciseActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uncasted = exceptionCatcher.catch {
            calculationService.startExercises(
                calculationApp.kindOfExercise!!,
                calculationApp.numberOfExercises!!
            )
        }
        exercises = if (uncasted is Exercises) uncasted else throw RuntimeException("not castable")
        val uncasted1 =
            exceptionCatcher.catch { calculationService.nextExercise(calculationApp.kindOfExercise!!) }
        exercise = if (uncasted1 is Exercise) uncasted1 else throw RuntimeException("not castable")
        initExercise(exercise)
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
                if (result.correct) {
                    binding.result.setBackgroundColor(Color.GREEN)
                    currentNumberOfExercise++
                    exceptionCatcher.catch {
                        if (currentNumberOfExercise > calculationApp.numberOfExercises!!) {
                            calculationService.createResultOfExercises(
                                exercises,
                                calculationApp.name!!
                            )
                            runOnUiThread { finish() }
                        } else {
                            val uncasted =
                                exceptionCatcher.catch {
                                    calculationService.nextExercise(
                                        calculationApp.kindOfExercise!!
                                    )
                                }
                            exercise =
                                if (uncasted is Exercise) uncasted else throw RuntimeException("Not Castable")
                        }
                    }
                    binding.result.setText("")
                    initExercise(exercise)
                } else binding.result.setBackgroundColor(Color.MAGENTA)
                return@setOnKeyListener true
            }
            return@setOnKeyListener super.onKeyUp(keyCode, event)
        }

    }

    private fun initExercise(exercise: Exercise) {
        binding.sign.text = exercise.kindOfExercise.sign
        binding.first.text = exercise.firstArgument.toString()
        binding.second.text = exercise.secondArgument.toString()
    }

}