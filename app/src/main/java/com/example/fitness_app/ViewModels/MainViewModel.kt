package com.example.fitness_app.ViewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness_app.db.ExerciseModel

class MainViewModel:ViewModel() {
    val mutavleListExetces = MutableLiveData<ArrayList<ExerciseModel>>()
    var pref: SharedPreferences? = null
    var currentDay = 0
    fun savePref(key: String, value: Int){
        pref?.edit()?.putInt(key,value)?.apply()
    }
    fun getPref(): Int{
        return pref?.getInt(currentDay.toString(),0) ?: 0
    }
}