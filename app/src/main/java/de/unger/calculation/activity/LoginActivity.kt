package de.unger.calculation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.unger.calculation.CalculationApp
import de.unger.calculation.databinding.LoginActivityLayoutBinding
import de.unger.domain.entities.KindOfExercise

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityLayoutBinding

    private lateinit var calculationApp: CalculationApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculationApp = application as CalculationApp
        binding = LoginActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.plus.setOnClickListener {
            calculationApp.kindOfExercise = KindOfExercise.PLUS
            startActivity()
        }
        binding.minus.setOnClickListener {
            calculationApp.kindOfExercise = KindOfExercise.MINUS
            startActivity()
        }
        binding.multiply.setOnClickListener {
            calculationApp.kindOfExercise = KindOfExercise.MULTIPLY
            startActivity()
        }
        binding.divide.setOnClickListener {
            calculationApp.kindOfExercise = KindOfExercise.DIVIDE
            startActivity()
        }
        binding.divide.setOnClickListener {
            calculationApp.kindOfExercise = KindOfExercise.REMAINDER
            startActivity()
        }
    }

    private fun startActivity() {
        if (binding.name.text.isNotBlank()) {
            calculationApp.name = binding.name.text.toString()
            startActivity(Intent(this, SelectActivity::class.java))
        }
    }

}