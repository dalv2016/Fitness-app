package com.example.fitness_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitness_app.Fragments.DaysFragment
import com.example.fitness_app.utils.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentManager.setFragment(DaysFragment.newInstance(),this)


    }
}