package de.unger.calculation.activity

import HighScoreAdapter
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.unger.calculation.CalculationApp
import de.unger.calculation.ExceptionCatcher
import de.unger.calculation.R
import de.unger.calculation.databinding.HighScoreActivityLayoutBinding

class HighScoreActivity : AppCompatActivity() {

    private lateinit var binding: HighScoreActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calculationApp = application as CalculationApp
        val calculationService = calculationApp.calculationService
        val exceptionCatcher = ExceptionCatcher(calculationApp, this)
        binding = HighScoreActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.difficulty.setOnClickListener {
            if (binding.difficulty.text.toString()
                    .lowercase() == "leicht"
            ) {
                binding.difficulty.text = getString(R.string.schwer1)
                binding.difficulty.setBackgroundColor(Color.rgb(255, 0, 0))
                calculationApp.easy = false
                exceptionCatcher.catch {
                    calculationService.findResults(
                        calculationApp.kindOfExercise!!,
                        calculationApp.easy
                    )
                }
                    ?.let {
                        binding.highScoreList.adapter = HighScoreAdapter(it)
                    }
            } else {
                binding.difficulty.text = getString(R.string.leicht1)
                binding.difficulty.setBackgroundColor(Color.rgb(0, 255, 0))
                calculationApp.easy = true
                exceptionCatcher.catch {
                    calculationService.findResults(
                        calculationApp.kindOfExercise!!,
                        calculationApp.easy
                    )
                }
                    ?.let {
                        binding.highScoreList.adapter = HighScoreAdapter(it)
                    }
            }
        }
        exceptionCatcher.catch {
            calculationService.findResults(
                calculationApp.kindOfExercise!!,
                calculationApp.easy
            )
        }
            ?.let {
                binding.highScoreList.adapter = HighScoreAdapter(it)
            }
    }

}