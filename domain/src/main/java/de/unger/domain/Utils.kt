import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class Utils {

    object CalculationDateTimeFormatter {
        val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(
                Locale.GERMANY
            )
    }
}