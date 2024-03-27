package com.example.fitness_app.Models.DayAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_app.Models.DataClass.DayModel
import com.example.fitness_app.R
import com.example.fitness_app.databinding.DaysListBinding
import com.example.fitness_app.intarfaces.Listener

class DayHolder(view : View): RecyclerView.ViewHolder(view){
    private val binding = DaysListBinding.bind(view)

    fun setData(day: DayModel, listener: Listener){
        val name = binding.root.context.getString(R.string.day) + " ${adapterPosition+ 1}"
        binding.tvName.text = name;
        val exCount = day.execises.split(",").size
        binding.tvExCounter.text = exCount.toString()
        binding.cardView2.setOnClickListener{
            listener.onClick(day)
        }
    }
}