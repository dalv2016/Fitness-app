package com.example.fitness_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.fitness_app.Fragments.DaysFragment
import com.example.fitness_app.Fragments.TrainingFragment
import com.example.fitness_app.utils.FragmentManager
import com.example.fitness_app.utils.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.pref = getSharedPreferences("main", MODE_PRIVATE)
        FragmentManager.setFragment(TrainingFragment(),this)
    }

    override fun onBackPressed() {
        if(FragmentManager.currentFrafment is DaysFragment) super.onBackPressed()
            // else FragmentManager.setFragment(DaysFragment.newInstance(),this)
    }
}