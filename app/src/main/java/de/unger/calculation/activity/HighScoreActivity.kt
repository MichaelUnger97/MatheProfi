package de.unger.calculation.activity

import HighScoreAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.unger.calculation.CalculationApp
import de.unger.calculation.ExceptionCatcher
import de.unger.calculation.databinding.HighScoreActivityLayoutBinding
import de.unger.domain.entities.ResultOfExercises

class HighScoreActivity : AppCompatActivity() {

    private lateinit var binding: HighScoreActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calculationApp = application as CalculationApp
        val calculationService = calculationApp.calculationService
        val exceptionCatcher = ExceptionCatcher(calculationApp, this)
        binding = HighScoreActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uncasted =
            exceptionCatcher.catch { calculationService.findResults(calculationApp.kindOfExercise!!) }
        val results = if (uncasted is List<*>) uncasted else throw RuntimeException()
        val resultsOfExercises =
            if (results.all { it is ResultOfExercises }) results as List<ResultOfExercises> else throw RuntimeException()
        binding.highScoreList.adapter = HighScoreAdapter(resultsOfExercises)
    }

}