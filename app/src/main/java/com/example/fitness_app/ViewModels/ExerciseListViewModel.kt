package com.example.fitness_app.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.db.MainDb
import com.example.fitness_app.utils.Objects.TrainingTopCardModel
import com.example.fitness_app.utils.Objects.TrainingUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(private val mainDb: MainDb):ViewModel() {
    val exerciseList = MutableLiveData<List<ExerciseModel>>()
    val topCardUpdate  = MutableLiveData<TrainingTopCardModel>()
    fun getDayExerciseList(day: DayModel?) = viewModelScope.launch {
        val day = day?.id?.let{
            mainDb.daysDao.getDayById(it)
        }
        if (day != null) {
            getTopCardData(day)
        }
        val allExerciseList = mainDb.exerciseDao.getAllExercises()
        val tmpExerciseList = ArrayList<ExerciseModel>()
         day?.let{dayModel ->
             dayModel.execises.split(",").forEach{id->
                 if(id.isEmpty())return@forEach
                 tmpExerciseList.add(allExerciseList.filter { it.id == id.toInt() }[0])
             }
             for (i in 0 until dayModel.doneExerciseCounter){
                 tmpExerciseList[i] = tmpExerciseList[i].copy(isDone = true)
         }
             exerciseList.value = tmpExerciseList
        }
    }

    fun getTopCardData(day: DayModel){
        var index = 0
        when(day.difficulty){
            "easy"-> {
                index=0;
            }
            "middle" -> {
                index=1
            }
            "hard" -> {
                index = 2
            }
        }
        topCardUpdate.value = TrainingUtils.topCardList[index].copy(
            progress = day.doneExerciseCounter,
            maxprogress = day.execises.split(",").size
        )
    }
}