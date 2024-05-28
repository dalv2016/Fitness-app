package com.example.fitness_app.Adapters

import android.animation.Animator
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
import dagger.hilt.android.AndroidEntryPoint
import pl.droidsonroids.gif.AnimationListener
import pl.droidsonroids.gif.GifDrawable

class ShooseExrciseAdapter(private val listener: Listener) : ListAdapter<ExerciseModel, ShooseExrciseAdapter.ExerciseHolder>(MyCoparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_exercise_list_item, parent,false)
        return ExerciseHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }




    class ExerciseHolder(view : View, val listener: Listener): RecyclerView.ViewHolder(view){
        private val binding = CustomExerciseListItemBinding.bind(view)
        init {
            binding.animationVie.addAnimatorListener(object: AnimationListener,
                Animator.AnimatorListener {
                override fun onAnimationCompleted(loopNumber: Int) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationStart(animation: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(animation: Animator) {
                    binding.animationVie.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(animation: Animator) {
                    TODO("Not yet implemented")
                }

            })
        }
        fun setData(exercise: ExerciseModel){
            binding.tvName.text = exercise.name
            binding.tvCounter.text = getTime(exercise.time)
            binding.imageView2.visibility =View.INVISIBLE
            binding.imageEx.setImageDrawable(GifDrawable(binding.root.context.assets, exercise.image))
            itemView.setOnClickListener{
                listener.Click(exercise.id ?: -1)
                binding.animationVie.visibility = View.VISIBLE
                binding.animationVie.playAnimation()
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
        fun Click(id: Int)
    }
}