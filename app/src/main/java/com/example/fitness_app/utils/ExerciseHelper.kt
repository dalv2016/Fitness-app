package com.example.fitness_app.utils

import com.example.fitness_app.db.ExerciseModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ExerciseHelper @Inject constructor() {

    fun createExerciseListWithRelax(list: List<ExerciseModel>) : List<ExerciseModel>{
        val tmpList = ArrayList<ExerciseModel>()
        list.forEachIndexed() {index, exercise ->
        tmpList.add(exercise.copy(time = "20", subTitle = if(index== 0) {"getReady"}else{"Relax"} ))
            tmpList.add(exercise.copy(subTitle = "Start" ))
        }
        tmpList.add(ExerciseModel(null,"Finish","Day finished", 0,"",false,"congrats.gif"))
        return tmpList
    }
    fun getExerciseOfTheDay(exercises: String, list: List<ExerciseModel>) : List<ExerciseModel>{
        val exercicesIdArray = exercises.split(",")
        val tmpList = ArrayList<ExerciseModel>()

        for(i in exercises.indices){
            if( exercicesIdArray[i].isNotEmpty()) {
                val exerciseId = exercicesIdArray[i].toInt()
                val exerciseItem = list.filter { it.id == exerciseId }[0]
                tmpList.add(exerciseItem)
            }
        }
        return tmpList
    }
}