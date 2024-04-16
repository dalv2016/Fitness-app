package com.example.fitness_app.Adapters.ExerciseAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.R

class ExrciseAdapter() : ListAdapter<ExerciseModel, ExerciseHolder>(MyCoparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent,false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }




}