package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness_app.Models.DataClass.DayModel
import com.example.fitness_app.Models.DataClass.ExerciseModel
import com.example.fitness_app.Models.DayAdapter.DaysAdapter
import com.example.fitness_app.intarfaces.Listener
import com.example.fitness_app.R
import com.example.fitness_app.databinding.DaysFragmentBinding
import com.example.fitness_app.utils.FragmentManager
import com.example.fitness_app.utils.MainViewModel
import kotlin.collections.ArrayList


class DaysFragment : Fragment(), Listener {

    private lateinit var binding: DaysFragmentBinding
    val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DaysFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    fun initRcView(){
        val adapter = DaysAdapter(this@DaysFragment)
        binding.rcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        binding.rcView.adapter = adapter
        adapter.submitList(fillDaysArray())
    }
    private fun fillDaysArray(): ArrayList<DayModel>{

        val tmpArray = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercises).forEach {
            tmpArray.add(DayModel(it,0,false))
        }
        return tmpArray
    }
    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
    private fun fillExerciseList(day: DayModel){
        val tmpList = ArrayList<ExerciseModel>()
        day.execises.split(",").forEach{
            val exerciseList =resources.getStringArray(R.array.exercises)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tmpList.add(ExerciseModel(exerciseArray[0],exerciseArray[1],false,exerciseArray[2]))
        }
        model.mutavleListExetces.value = tmpList
        //model.mutavleListExetces.observe(viewLifecycleOwner,{})

    }
    override fun onClick(day: DayModel) {
        fillExerciseList(day)
        model.currentDay = day.dayNumber
        FragmentManager.setFragment(ExercisesListFragment.newInstance(),activity as AppCompatActivity)
    }
}