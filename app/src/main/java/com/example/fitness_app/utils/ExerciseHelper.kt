package com.example.fitness_app.utils

import com.example.fitness_app.db.ExerciseModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ExerciseHelper @Inject constructor() {

    fun createExerciseListWithRelax(list: List<ExerciseModel>) : List<ExerciseModel>{
        val tmpList = ArrayList<ExerciseModel>()
        tmpList.forEach { exercise ->
        tmpList.add(exercise.copy(time = "20", subTitle = "Relax" ))
            tmpList.add(exercise.copy(subTitle = "Start" ))
        }
        tmpList.add(ExerciseModel(null,"Finish","Day finished", 0,"",false,"congrats.gif"))
        return tmpList
    }
    fun getExerciseOfTheDay(exercises: String, list: List<ExerciseModel>) : List<ExerciseModel>{
        val exercicesIndexArray = exercises.split(",")
        val tmpList = ArrayList<ExerciseModel>()

        for(i in exercises.indices){
            tmpList.add(list[exercicesIndexArray[i].toInt()])
        }
        return tmpList
    }
}