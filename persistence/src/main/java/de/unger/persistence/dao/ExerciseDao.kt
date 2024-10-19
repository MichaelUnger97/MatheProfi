package de.unger.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import de.unger.persistence.entity.ExerciseEntity

@Dao
interface ExerciseDao {

    @Insert
    fun insert(exerciseEntity: ExerciseEntity)

    @Query("Select * From ExerciseEntity Where ExerciseEntity.`index`=:index ")
    fun findByIndex(index: Int): ExerciseEntity

    @Query("Select Count(*)From ExerciseEntity")
    fun numberOfExerciseEntities(): Int
}