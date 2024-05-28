package com.example.fitness_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.R
import com.example.fitness_app.databinding.CustomDayListItemBinding
import com.example.fitness_app.databinding.CustomExerciseListItemBinding
import com.example.fitness_app.utils.Objects.Time
import pl.droidsonroids.gif.GifDrawable

class CustomExrciseAdapter(private val listener: Listener) : ListAdapter<ExerciseModel, CustomExrciseAdapter.ExerciseHolder>(MyCoparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_exercise_list_item, parent,false)
        return ExerciseHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }




    class ExerciseHolder(view : View, val listener: Listener): RecyclerView.ViewHolder(view){
        private val binding = CustomExerciseListItemBinding.bind(view)

        fun setData(exercise: ExerciseModel){
            binding.tvName.text = exercise.name
            binding.tvCounter.text = getTime(exercise.time)
            binding.imageEx.setImageDrawable(GifDrawable(binding.root.context.assets, exercise.image))
            binding.imageView2.setOnClickListener{
                listener.Delete(adapterPosition)
            }
        }
        fun getTime(time: String): String{
            return if (time.startsWith("x")){
                time
            }
            else{
                Time.getTime(time.toLong()+1000)
            }
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

    interface Listener{
        fun Delete(position: Int)
    }
}