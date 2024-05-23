package com.example.fitness_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.R
import com.example.fitness_app.databinding.ExerciseListItemBinding
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.utils.Objects.Time
import pl.droidsonroids.gif.GifDrawable

class ExrciseAdapter() : ListAdapter<ExerciseModel, ExrciseAdapter.ExerciseHolder>(MyCoparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent,false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }




    class ExerciseHolder(view : View): RecyclerView.ViewHolder(view){
        private val binding = ExerciseListItemBinding.bind(view)

        fun setData(exercise: ExerciseModel){
            binding.tvName.text = exercise.name
            binding.tvCounter.text = Time.getTime(exercise.time.toLong())
            binding.imageEx.setImageDrawable(GifDrawable(binding.root.context.assets, exercise.image))
            binding.imageView2.visibility = if(exercise.isDone) View.VISIBLE else View.INVISIBLE
        }

        private fun getExerciseProgress(day: DayModel):String{
            val totalExercise = day.execises.split(",").size
            return if(day.isDone) {
                "Done"
            }else {
                "$totalExercise " + "exercises | Progress: " + (day.doneExerciseCounter * 100) / totalExercise + "%"
            }
            }

        }
        private fun getTime(time: String): String{
            return if (time.startsWith("x")){
                time
            }
            else{
                Time.getTime(time.toLong()+1000)
            }
        }
    class MyCoparator : DiffUtil.ItemCallback<ExerciseModel>() {

        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem ==newItem
        }
    }

}