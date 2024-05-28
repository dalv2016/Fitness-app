package com.example.fitness_app.ViewModels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.db.MainDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomDaysViewModel @Inject constructor(private val mainDb: MainDb) :ViewModel() {
    val daysList = mainDb.daysDao.getDaysByDifficulty("custom").asLiveData(Dispatchers.Main)

    fun insertDay(dayModel: DayModel) = viewModelScope.launch {
        mainDb.daysDao.insertDay(dayModel)
    }

    fun deleteDay(dayModel: DayModel) = viewModelScope.launch {
        mainDb.daysDao.deleteDay(dayModel)
    }
}