package com.example.fitness_app.intarfaces.Dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fitness_app.db.ExerciseModel

@Dao
interface ExerciseDao {
    @Query("Select * From exercise_table")
    suspend fun getAllExercises(): List<ExerciseModel>
}