package com.example.fitness_app.intarfaces

import com.example.fitness_app.db.DayModel

interface Listener {

    fun onClick(day: DayModel)

}