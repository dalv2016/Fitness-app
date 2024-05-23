package com.example.fitness_app.utils.ViewModels

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.db.MainDb
import com.example.fitness_app.db.StatisticModel
import com.example.fitness_app.utils.ExerciseHelper
import com.example.fitness_app.utils.Objects.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val mainDb: MainDb,
    private val exerciseHelper: ExerciseHelper
): ViewModel()  {

    var curruntDay: DayModel? = null
    var updateExercise = MutableLiveData<ExerciseModel>()
    var updateTime = MutableLiveData<Long>()
    var updateToolbar = MutableLiveData<String>()
    var statisticModel: StatisticModel? = null

    private var timer: CountDownTimer? = null
    private var exerciseListWithRelax: List<ExerciseModel> = emptyList()
    private var doneExerciseCounter = 0
    private var doneExerciseCounterToSAve = 0
    private var totalExerciseNumber = 0

    private fun updateDay(day: DayModel) = viewModelScope.launch {
        mainDb.daysDao.insertDay(day)
    }

    private fun dayDone(){
        if(totalExerciseNumber == doneExerciseCounterToSAve-1) {
            curruntDay = curruntDay?.copy(isDone = true)
            curruntDay?.let { updateDay(it) }
        }
    }
     fun getExercise(day: DayModel){ viewModelScope.launch {
         curruntDay = day.id?.let { mainDb.daysDao.getDayById(it) }

             val exerciseList = mainDb.exerciseDao.getAllExercises()
             val dayExercises = exerciseHelper.getExerciseOfTheDay(day.execises,exerciseList)
             doneExerciseCounterToSAve = curruntDay?.doneExerciseCounter ?: 0
             totalExerciseNumber = day.execises.split(",").size
             exerciseListWithRelax= exerciseHelper.createExerciseListWithRelax(dayExercises.subList(curruntDay?.doneExerciseCounter ?: 0, dayExercises.size))
         }
         getStatistic()
        nextExercise()
     }

    fun nextExercise(){
        timer?.cancel()
        updateToolBar()
        val exercise = exerciseListWithRelax[doneExerciseCounter++]
        updateExercise.value = exercise
    }

    private fun updateToolBar(){
        if(doneExerciseCounter%2==0) {
            val text = "Done: ${doneExerciseCounterToSAve++} / $totalExerciseNumber"
            updateToolbar.value = text
        }
    }
    fun startTimer (time: Long) {

        timer = object : CountDownTimer((time +1) *1000,1){
            override fun onTick(restTime: Long) {
                updateTime.value = restTime
            }
            override fun onFinish() {

                nextExercise()
            }
        }.start()
    }
    fun getStatistic() = viewModelScope.launch {
        val currentDate = Time.getCurrentDay()
        statisticModel = mainDb.statisticsDao.getStatisticByDate(currentDate)
    }
    fun createStatictic(): StatisticModel{
        var kcal = 0
        var time = 0
        exerciseListWithRelax.subList(0,doneExerciseCounterToSAve-1).forEach{model ->
            kcal+=model.kcal
            time+=getTimeFromExercise(model)
        }
        return statisticModel?.copy(kcal = kcal, workoutTime = time.toString()) ?: StatisticModel(null, Time.getCurrentDay(),kcal,time.toString())
    }
    fun getTimeFromExercise(exerciseModel: ExerciseModel):Int{
        return if(exerciseModel.time.startsWith("x")){
            exerciseModel.time.replace("x","").toInt()*2
        }
        else{
            exerciseModel.time.toInt()
        }

    }
    fun  onPause(){
        timer?.cancel()
        dayDone()
        updateDay(curruntDay!!.copy(doneExerciseCounter = if(doneExerciseCounterToSAve>0){
            doneExerciseCounter--
        }
        else{
            0
        }))
        viewModelScope.launch {
            mainDb.statisticsDao.insertDayStatistic(createStatictic())
        }
    }
}