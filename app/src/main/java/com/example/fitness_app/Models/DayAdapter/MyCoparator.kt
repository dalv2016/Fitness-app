package com.example.fitness_app.Models.DayAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitness_app.Models.DataClass.DayModel

class MyCoparator : DiffUtil.ItemCallback<DayModel>() {
    override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return  oldItem ==newItem
    }

    override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return  oldItem ==newItem
    }
}