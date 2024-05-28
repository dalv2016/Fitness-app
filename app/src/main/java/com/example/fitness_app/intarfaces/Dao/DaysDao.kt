package com.example.fitness_app.intarfaces.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_app.db.DayModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(dayModel: DayModel)

    @Query("Select * From day_model_table Where id = :dayId ")
    suspend fun getDayById(dayId: Int):DayModel

    @Query("SELECT * FROM day_model_table WHERE difficulty = :difficulty")
    fun getDaysByDifficulty(difficulty: String):Flow<List<DayModel>>

    @Query("Select * From day_model_table")
     suspend fun getAllDAys():List<DayModel>

     @Delete
     suspend fun deleteDay(dayModel: DayModel)
}