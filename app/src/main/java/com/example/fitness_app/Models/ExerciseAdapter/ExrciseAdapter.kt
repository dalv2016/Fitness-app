package com.example.fitness_app.Models.ExerciseAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.fitness_app.Models.DataClass.ExerciseModel
import com.example.fitness_app.R
import com.example.fitness_app.intarfaces.Listener

class ExrciseAdapter() : ListAdapter<ExerciseModel, ExerciseHolder>(MyCoparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent,false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }




}