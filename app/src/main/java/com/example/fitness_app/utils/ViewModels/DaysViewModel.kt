package com.example.fitness_app.utils.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.db.MainDb
import com.example.fitness_app.utils.Objects.TrainingTopCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(private val mainDb:MainDb)  : ViewModel()  {
    val daysList  = MutableLiveData<List<DayModel>>()
    val topCardUpdate  = MutableLiveData<TrainingTopCardModel>()
fun getExerciseDayByDifficulty(trainingTopCardModel: TrainingTopCardModel){
    viewModelScope.launch {
        mainDb.daysDao.getDaysByDifficulty(trainingTopCardModel.difficulty).collect{list ->
            daysList.value = list
            topCardUpdate.value=  trainingTopCardModel.copy(maxprogress = list.size, progress = getProgress(list))
        }
    }
}
fun getProgress(list: List<DayModel>):Int{
    var counter =0;
    list.forEach{day->
        if(day.isDone){
            counter++
        }
    }
    return counter
}
}