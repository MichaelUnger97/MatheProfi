package de.unger.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import de.unger.persistence.entity.ResultOfExerciseEntity
import java.time.LocalDateTime

@Dao
interface ResultOfExerciseDao {

    @Insert
    fun insert(resultOfExerciseEntity: ResultOfExerciseEntity)

    @Query("Select * From ResultOfExerciseEntity Where ResultOfExerciseEntity.correct=0 order By ResultOfExerciseEntity.resultDateTime Desc")
    fun findMistakes(): List<ResultOfExerciseEntity>

    @Query("Select * From ResultOfExerciseEntity Where ResultOfExerciseEntity.resultDateTime > :startTime And ResultOfExerciseEntity.resultDateTime < :endTime ")
    fun findCurrentMistakes(
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): List<ResultOfExerciseEntity>
}