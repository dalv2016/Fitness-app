package com.example.fitness_app.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness_app.Models.DataClass.ExerciseModel

class MainViewModel:ViewModel() {
    val mutavleListExetces = MutableLiveData<ArrayList<ExerciseModel>>()
}