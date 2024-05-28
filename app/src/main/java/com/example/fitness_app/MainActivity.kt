package com.example.fitness_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.fitness_app.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val controller = findNavController(R.id.fragmentContainerView )
        setupWithNavController(binding.bottomNavigationView,controller)
       controller.addOnDestinationChangedListener{_,destination,_ ->
           when(destination.id){
               R.id.customDaysListFragment -> {
                   binding.bottomNavigationView.visibility = View.GONE
               }
               R.id.customExercisesListFragment -> {
                   binding.bottomNavigationView.visibility = View.GONE
               }
               R.id.chooseExerciseFragment -> {
                   binding.bottomNavigationView.visibility = View.GONE
               }
               R.id.exercisesListFragment -> {
                   binding.bottomNavigationView.visibility = View.GONE
               }
               R.id.exerciseFragment -> {
                   binding.bottomNavigationView.visibility = View.GONE
               }
               else-> {
                   binding.bottomNavigationView.visibility = View.VISIBLE
               }
           }
       }
    }
}