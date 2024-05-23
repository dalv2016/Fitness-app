package com.example.fitness_app.utils.ViewModels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val mainDb: MainDb) :ViewModel() {

  fun clear() = viewModelScope.launch{
      val daysList  = mainDb.daysDao.getAllDAys()
        daysList.forEach { day->
            mainDb.daysDao.insertDay(day.copy(doneExerciseCounter = 0, isDone = false))
        }
        mainDb.statisticsDao.deleteData()
  }
}