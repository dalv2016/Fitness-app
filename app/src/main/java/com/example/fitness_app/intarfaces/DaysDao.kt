package com.example.fitness_app.intarfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_app.db.DayModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(dayModel: DayModel)

    @Query("Select * From dau_model_table Where id = :dayId ")
    suspend fun getDayById(dayId: Int):DayModel

    @Query("Select * From dau_model_table Where difficulty = :difficulty")
    suspend fun getDaysByDifficulty(difficulty: String):Flow<List<DayModel>>
}