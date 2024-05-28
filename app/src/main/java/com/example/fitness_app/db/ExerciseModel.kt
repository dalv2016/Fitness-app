package com.example.fitness_app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class ExerciseModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var subTitle: String,
    var kcal: Int,
    var time: String,
    var isDone: Boolean,
    var image: String
)
