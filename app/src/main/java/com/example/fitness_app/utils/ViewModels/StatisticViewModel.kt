package com.example.fitness_app.utils.ViewModels

import android.app.usage.UsageEvents.Event
import android.provider.CalendarContract.EventDays
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applandeo.materialcalendarview.EventDay
import com.example.fitness_app.R
import com.example.fitness_app.db.MainDb
import com.example.fitness_app.db.StatisticModel
import com.example.fitness_app.db.WeightModel
import com.example.fitness_app.utils.Objects.DataSelectorModel
import com.example.fitness_app.utils.Objects.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(private val mainDb: MainDb):ViewModel() {
    val eventListData = MutableLiveData<List<EventDay>>()
    val statisticData = MutableLiveData<StatisticModel>()
    val weightListData = MutableLiveData<List<WeightModel>>()
    val yearListData = MutableLiveData<List<DataSelectorModel>>()
    var year = -1
    var month = Calendar.getInstance().get(Calendar.MONTH)
    fun getStatisticEvents() = viewModelScope.launch {
        val eventList = ArrayList<EventDay>()
        val statisticList = mainDb.statisticsDao.getStatistics()
        statisticList.forEach{statisticModel ->
            eventList.add(EventDay(Time.getCalendarFromDate(statisticModel.date), R.drawable.star))
        }
        eventListData.value = eventList
    }

    fun getStatisticBuDate(date: String) = viewModelScope.launch {
        statisticData.value = mainDb.statisticsDao.getStatisticByDate(date) ?: StatisticModel(null,date,0,"0")

    }
    fun getWeightByYearAndMonth() = viewModelScope.launch {
        weightListData.value = mainDb.weightDao.getMonthWeightList(year,month)
    }
    fun saveWeight(weight: Int) = viewModelScope.launch {
        val caledar = Calendar.getInstance()
        mainDb.weightDao.insertWeight(WeightModel(null, weight,caledar.get(Calendar.DAY_OF_MONTH),caledar.get(Calendar.MONTH),caledar.get(Calendar.YEAR)))
    }
    fun yearList() = viewModelScope.launch {
        val tmpYearList = ArrayList<DataSelectorModel>()
        val weightList = mainDb.weightDao.getAllWeightList()
        weightList.forEach { weightModel ->
            if(!tmpYearList.any{it.text == weightModel.year.toString()}){
                tmpYearList.add(DataSelectorModel(weightModel.year.toString()))
            }
        }
        yearListData.value = tmpYearList
    }
    }