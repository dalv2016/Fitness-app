package com.example.fitness_app.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "day_model_table")
data class DayModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var execises: String,
    var dayNumber: Int,
    var isDone : Boolean,
    var difficulty: String,
    var doneExerciseCounter: Int
):Serializable
