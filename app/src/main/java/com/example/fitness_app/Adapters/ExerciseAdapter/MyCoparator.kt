package com.example.fitness_app.Adapters.ExerciseAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitness_app.db.ExerciseModel

class MyCoparator : DiffUtil.ItemCallback<ExerciseModel>() {

    override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem ==newItem
    }

    override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
        return oldItem ==newItem
    }
}