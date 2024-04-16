package com.example.fitness_app.Adapters.DayAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.R
import com.example.fitness_app.intarfaces.Listener

class DaysAdapter(var listener: Listener) : ListAdapter<DayModel, DayHolder>(MyCoparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.days_list, parent,false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.setData(getItem(position),listener)
    }




}