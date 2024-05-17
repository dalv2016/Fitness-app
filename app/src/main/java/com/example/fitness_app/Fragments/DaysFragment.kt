package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.Adapters.DaysAdapter
import com.example.fitness_app.R
import com.example.fitness_app.intarfaces.Listener
import com.example.fitness_app.databinding.DaysFragmentBinding
import com.example.fitness_app.intarfaces.DialogListener
import com.example.fitness_app.utils.ViewModels.DaysViewModel
import com.example.fitness_app.utils.Objects.DialogManager


class DaysFragment : Fragment(), Listener {

    private lateinit var binding: DaysFragmentBinding
    private lateinit var adapter: DaysAdapter

    val model: DaysViewModel by activityViewModels()
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
        adapter = DaysAdapter(this@DaysFragment)
        binding.rcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        binding.rcView.itemAnimator = null
        binding.rcView.adapter = adapter

    }
   /* private fun fillDaysArray(): ArrayList<DayModel>{

        val tmpArray = ArrayList<DayModel>()
        var doneDaysCounter = 0
        resources.getStringArray(R.array.day_exercises).forEach {
            model.currentDay++
            val exCounter = it.split(",").size
          //  tmpArray.add(DayModel(it,0,model.getPref() == exCounter))
        }
        binding.progressBar2.max = tmpArray.size
        tmpArray.forEach{
            if(it.isDone) doneDaysCounter++
        }
        updateDaysProgress(tmpArray.size - doneDaysCounter, tmpArray.size)
        return tmpArray
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.reset){
            DialogManager.showDialog(activity as AppCompatActivity, "Начать день заново?", object : DialogListener{
                override fun onClick() {
                        model.pref?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
                }
            })
        }
        return super.onOptionsItemSelected(item)
    }*/
    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
        }

    private  fun updateDaysProgress(restDays: Int, maxDay:Int){
        val rDays = "Осталось $restDays дней"
        //binding.textView2.text = rDays
        //binding.progressBar2.progress = maxDay-restDays
    }
    private fun updateAdapter() {
            model.daysList.observe(viewLifecycleOwner) { list ->
                adapter.submitList(list)
            }
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }
    /*private fun fillExerciseList(day: DayModel){
        val tmpList = ArrayList<ExerciseModel>()
        day.execises.split(",").forEach{
            val exerciseList =resources.getStringArray(R.array.exercises)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
          //  tmpList.add(ExerciseModel(exerciseArray[0],exerciseArray[1],false,exerciseArray[2]))
        }
        model.mutavleListExetces.value = tmpList
        //model.mutavleListExetces.observe(viewLifecycleOwner,{})

    }*/
    override fun onClick(day: DayModel) {
        if(day.isDone) {
/*fillExerciseList(day)
model.currentDay = day.dayNumber
FragmentManager.setFraent(
    ExercisesListFragment.newInstance(),
    activity as AppCompatActivity
)
}
else {*/
    DialogManager.showDialog(activity as AppCompatActivity, "Начать день заново?", object : DialogListener{
        override fun onClick() {
            /*model.savePref(day.dayNumber.toString(), 0)
            fillExerciseList(day)
            model.currentDay = day.dayNumber
            FragmentManager.setFragment(
                ExercisesListFragment.newInstance(),
                activity as AppCompatActivity)*/
        }
    })
}
        else{
        openEcersiceListFragment(day)
        }
}

    fun openEcersiceListFragment(day:DayModel){
        val bundle = Bundle().apply {
            putSerializable("day",day)
        }
        findNavController().navigate(R.id.action_trainingFragment_to_exercisesListFragment, bundle)
    }
}