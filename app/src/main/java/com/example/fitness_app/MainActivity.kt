package com.example.fitness_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fitness_app.Fragments.DaysFragment
import com.example.fitness_app.Fragments.TrainingFragment
import com.example.fitness_app.databinding.ActivityMainBinding
import com.example.fitness_app.databinding.FragmentStatisticBinding
import com.example.fitness_app.utils.Objects.FragmentManager
import com.example.fitness_app.utils.ViewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val controller = findNavController(R.id.fragmentContainerView )
        setupWithNavController(binding.bottomNavigationView,controller)
       controller.addOnDestinationChangedListener{_,desttination,_ ->
           when(desttination.id){
               R.id.customExerciseListFragment -> {
                   binding.bottomNavigationView.visibility = View.GONE
               }
               else-> {
                   binding.bottomNavigationView.visibility = View.VISIBLE
               }
           }
       }
    }

    override fun onBackPressed() {
        if(FragmentManager.currentFrafment is DaysFragment) super.onBackPressed()
            // else FragmentManager.setFragment(DaysFragment.newInstance(),this)
    }
}