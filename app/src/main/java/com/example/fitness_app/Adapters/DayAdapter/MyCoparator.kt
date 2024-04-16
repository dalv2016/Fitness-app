package com.example.fitness_app.Adapters.DayAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitness_app.db.DayModel

class MyCoparator : DiffUtil.ItemCallback<DayModel>() {
    override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return  oldItem ==newItem
    }

    override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return  oldItem ==newItem
    }
}