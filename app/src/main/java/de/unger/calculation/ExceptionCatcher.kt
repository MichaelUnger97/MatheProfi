package de.unger.calculation

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import de.unger.domain.Constants
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class ExceptionCatcher(
    private val appContext: CalculationApp, val activity: Activity
) : AlertDialog(activity) {
    fun catch(callable: Callable<Any?>): Any? {
        return try {
            appContext.executorService.submit(
                callable
            ).get(
                appContext.getSharedPreferences(
                    Constants.SETTINGS.get()
                            as String, Context.MODE_PRIVATE
                )
                    .getLong(
                        Constants.TIMEOUT_SECONDS.get() as String,
                        Constants.DEFAULT_TIMEOUT_SECONDS.get() as Long
                    ), TimeUnit.SECONDS
            )
        } catch (ex: Exception) {
            this.setMessage(ex.message.toString())
            activity.runOnUiThread { this.show() }
            //TODO ex.printStackTrace()
        }

    }
}