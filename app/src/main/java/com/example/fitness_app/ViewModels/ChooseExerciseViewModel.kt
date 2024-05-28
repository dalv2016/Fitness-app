package com.example.fitness_app.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseExerciseViewModel @Inject constructor( private val mainDb: MainDb):ViewModel() {
    val exercisesListData = MutableLiveData<List<ExerciseModel>>()
    private var dayModel: DayModel?= null

    fun getAllExercises() = viewModelScope.launch {
        exercisesListData.value =  mainDb.exerciseDao.getAllExercises()
    }

    fun getDayById(id: Int)= viewModelScope.launch {
        dayModel = mainDb.daysDao.getDayById(id)
    }

    fun updateDayById(exercise: String)= viewModelScope.launch {
        val  olExercises = dayModel?.execises ?: ""
        val tmpExercise= if(olExercises.isEmpty()){
            exercise.replaceFirst(",","")
        }
        else{
            exercise
        }

         dayModel?.copy(execises = olExercises+exercise)?.let { mainDb.daysDao.insertDay(it) }
    }
}