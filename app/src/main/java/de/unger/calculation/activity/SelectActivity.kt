package de.unger.calculation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import de.unger.calculation.CalculationApp
import de.unger.calculation.databinding.SelectActivityLayoutBinding

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: SelectActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calculationApp = application as CalculationApp
        binding = SelectActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.setOnClickListener {
            val numberOfExercises = binding.numberOfExercises.text.toString().toIntOrNull()
            if (numberOfExercises != null) {
                if (numberOfExercises < 5) {
                    AlertDialog.Builder(this).setMessage("Mindestens 5 Aufgaben wählen").create()
                        .show()
                } else {
                    calculationApp.numberOfExercises = numberOfExercises
                    startActivity(Intent(this, ExerciseActivity::class.java))
                }
            }
        }
        binding.results.setOnClickListener {
            startActivity(Intent(this, HighScoreActivity::class.java))
        }
        binding.mistakes.setOnClickListener {
            startActivity(Intent(this, MistakesActivity::class.java))
        }

    }

}