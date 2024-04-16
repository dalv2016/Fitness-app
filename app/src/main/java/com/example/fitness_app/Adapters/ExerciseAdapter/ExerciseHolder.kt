package com.example.fitness_app.Adapters.ExerciseAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.databinding.ExerciseListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExerciseHolder(view : View): RecyclerView.ViewHolder(view){
    private val binding = ExerciseListItemBinding.bind(view)

    fun setData(exercise: ExerciseModel){
        binding.tvName.text = exercise.name
        binding.tvCounter.text = exercise.time
        binding.imageEx.setImageDrawable(GifDrawable(binding.root.context.assets, exercise.image))
        binding.checkBoxDoneEx.isChecked = exercise.isDone
    }
}