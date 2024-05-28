package com.example.fitness_app.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.db.MainDb
import com.example.fitness_app.utils.ExerciseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomExercisesListViewModel @Inject constructor(private  val mainDb: MainDb,private val  exerciseHelper: ExerciseHelper) : ViewModel(){
    val listExercisesData = MutableLiveData<List<ExerciseModel>>()
    private var dayModel:DayModel?= null
    fun getExercises(id: Int) = viewModelScope.launch {
        dayModel = mainDb.daysDao.getDayById(id)
        val exerciseList = mainDb.exerciseDao.getAllExercises()
        listExercisesData.value = exerciseHelper.getExerciseOfTheDay(dayModel?.execises!!,exerciseList)
    }

    fun updateDay(exercises: String) = viewModelScope.launch {
            val tmpExercise= exercises.replaceFirst(",","")
        mainDb.daysDao.insertDay(dayModel?.copy(doneExerciseCounter = 0, isDone = false, execises = tmpExercise)!!)
    }
}