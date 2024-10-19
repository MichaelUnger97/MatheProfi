package de.unger.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import de.unger.domain.entities.KindOfExercise
import de.unger.persistence.entity.ResultOfExercisesEntity

@Dao
interface ResultOfExercisesDao {

    @Insert
    fun insert(resultOfExercisesEntity: ResultOfExercisesEntity)

    @Query(
        "Select * From ResultOfExercisesEntity " +
                "Where ResultOfExercisesEntity.kindOfExercises=:kindOfExercise " +
                "Order By 1.0*ResultOfExercisesEntity.mistakes/ResultOfExercisesEntity.exercises  asc," +
                " ResultOfExercisesEntity.endTime-ResultOfExercisesEntity.startTime asc"
    )
    fun findResults(kindOfExercise: KindOfExercise): List<ResultOfExercisesEntity>

    @Query(
        "Select * From ResultOfExercisesEntity Where " +
                "endTime=(Select MIN(endTime) From ResultOfExercisesEntity)"
    )
    fun findLastResult(): ResultOfExercisesEntity?
}