package com.example.fitness_app.Models.ExerciseAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitness_app.Models.DataClass.DayModel
import com.example.fitness_app.Models.DataClass.ExerciseModel

class MyCoparator : DiffUtil.ItemCallback<ExerciseModel>() {

    override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem ==newItem
    }

    override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem ==newItem
    }
}