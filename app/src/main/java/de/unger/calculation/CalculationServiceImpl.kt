package de.unger.calculation

import android.content.Context
import de.unger.domain.CalculationService
import de.unger.persistence.adapter.ExerciseAdapter
import de.unger.persistence.adapter.ResultOfExerciseAdapter
import de.unger.persistence.adapter.ResultOfExercisesAdapter

class CalculationServiceImpl(private val context: Context) {
    fun get() = CalculationService(
        ResultOfExerciseAdapter(context),
        ResultOfExercisesAdapter(context),
        ExerciseAdapter(context)
    )
}