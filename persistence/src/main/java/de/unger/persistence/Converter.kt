package de.unger.persistence

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converter {

    @TypeConverter
    fun localDateTimeFromString(dateString: String): LocalDateTime? {
        val localDateTime = LocalDateTime.parse(dateString)
        return if (localDateTime == LocalDateTime.of(1000, 1, 1, 0, 0)) null else localDateTime
    }

    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime?): String =
        localDateTime?.toString() ?: LocalDateTime.of(1000, 1, 1, 0, 0).toString()
}