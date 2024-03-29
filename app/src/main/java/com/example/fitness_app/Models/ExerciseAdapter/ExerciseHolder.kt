package com.example.fitness_app.Models.ExerciseAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_app.Fragments.ExercisesListFragment
import com.example.fitness_app.Models.DataClass.DayModel
import com.example.fitness_app.Models.DataClass.ExerciseModel
import com.example.fitness_app.R
import com.example.fitness_app.databinding.DaysListBinding
import com.example.fitness_app.databinding.ExerciseListItemBinding
import com.example.fitness_app.databinding.ExercisesListFragmentBinding
import com.example.fitness_app.intarfaces.Listener
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