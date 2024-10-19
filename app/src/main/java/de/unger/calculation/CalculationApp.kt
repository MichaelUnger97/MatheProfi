package de.unger.calculation

import android.app.Application
import de.unger.domain.CalculationService
import de.unger.domain.entities.KindOfExercise
import java.util.concurrent.Executors

class CalculationApp : Application() {
    var name: String? = null
    var kindOfExercise: KindOfExercise? = null
    var numberOfExercises: Int? = null
    val executorService = Executors.newFixedThreadPool(3)!!
    lateinit var calculationService: CalculationService
    override fun onCreate() {
        super.onCreate()
        calculationService = CalculationServiceImpl(this).get()
    }
}