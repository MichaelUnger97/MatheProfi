package de.unger.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import de.unger.persistence.dao.ExerciseDao
import de.unger.persistence.dao.ResultOfExerciseDao
import de.unger.persistence.dao.ResultOfExercisesDao
import de.unger.persistence.entity.ExerciseEntity
import de.unger.persistence.entity.ResultOfExerciseEntity
import de.unger.persistence.entity.ResultOfExercisesEntity


@Database(
    version = 20241019,
    entities = [
        ResultOfExerciseEntity::class,
        ResultOfExercisesEntity::class,
        ExerciseEntity::class
    ]
)
@TypeConverters(
    Converter::class
)
abstract class HerdenDatabase : RoomDatabase() {
    abstract fun ResultOfExerciseDao(): ResultOfExerciseDao
    abstract fun ResultOfExercisesDao(): ResultOfExercisesDao
    abstract fun ExerciseDao(): ExerciseDao

    object DatabaseBuilder {
        private var INSTANCE: HerdenDatabase? = null
        fun getInstance(context: Context): HerdenDatabase {
            if (INSTANCE == null) {
                synchronized(HerdenDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context): HerdenDatabase {
            val MIGRATION_20240707_20241019 = object : Migration(20240707, 20241019) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE ExerciseEntity ADD COLUMN result2 INTEGER")
                }
            }
            return Room.databaseBuilder(
                context.applicationContext,
                HerdenDatabase::class.java,
                "HerdenDatabase"
            ).addMigrations(MIGRATION_20240707_20241019).build()
        }
    }

}