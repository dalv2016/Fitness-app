package com.example.fitness_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_app.R
import com.example.fitness_app.databinding.CustomDayListItemBinding
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.databinding.FragmentCustomDaysListBinding

class CustomDayAdapter(var listener: Listener) : ListAdapter<DayModel, CustomDayAdapter.DayHolder>(MyCoparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_day_list_item, parent,false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.setData(getItem(position),listener)
    }




    class DayHolder(view : View): RecyclerView.ViewHolder(view){
        private val binding = CustomDayListItemBinding.bind(view)

        fun setData(day: DayModel, listener: Listener){
            val name = binding.root.context.getString(R.string.day) + " ${adapterPosition+ 1}"
            binding.tvName.text = name;
            val exCount = getExerciseProgress(day)
            binding.tvExCounter.text = exCount
            binding.imgDayDone.setOnClickListener{
                listener.onClick(day.copy(dayNumber = adapterPosition+1))
            }
            binding.btnDelete.setOnClickListener{
                listener.onDelete(day)
            }
        }

        private fun getExerciseProgress(day: DayModel):String{
            if(day.execises.isEmpty()) return  "0 exercises"
            val totalExercise = day.execises.split(",").size
            return if(day.isDone) {
                "Done"
            }else {
                "$totalExercise " + "exercises | Progress: " + (day.doneExerciseCounter * 100) / totalExercise + "%"
            }
        }
    }




    class MyCoparator : DiffUtil.ItemCallback<DayModel>() {
        override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return  oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return  oldItem ==newItem
        }
    }
    interface Listener {

        fun onClick(day: DayModel)
        fun onDelete(day: DayModel)

    }

}