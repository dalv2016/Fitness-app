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
    override fun onClick(day: DayModel) {
        if(day.isDone) {
    DialogManager.showDialog(activity as AppCompatActivity, "Начать день заново?", object : DialogManager.Listener{
        override fun onClick() {
            model.resetSelectDay(day)
            openEcersiceListFragment(day)
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