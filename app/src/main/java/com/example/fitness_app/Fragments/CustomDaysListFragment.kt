package com.example.fitness_app.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness_app.Adapters.CustomDayAdapter
import com.example.fitness_app.R
import com.example.fitness_app.databinding.FragmentCustomExerciseListBinding
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.ViewModels.CustomDaysViewModel
import com.example.fitness_app.databinding.FragmentCustomDaysListBinding
import com.example.fitness_app.utils.Objects.DialogManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDaysListFragment: Fragment(), CustomDayAdapter.Listener {
    private var _binding: FragmentCustomDaysListBinding? = null
    private lateinit var daysAdapter: CustomDayAdapter
    private val binding get() = _binding!!
    private  val model: CustomDaysViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomDaysListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Custom days"
        binding.btnAddDay.setOnClickListener{
            model.insertDay(DayModel(null,"",0,false,"custom",0))
        }
        observerDaysList()
        initRc()
    }
    private fun initRc(){
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            daysAdapter = CustomDayAdapter(this@CustomDaysListFragment)
            rcView.adapter= daysAdapter
        }
    }
    private fun observerDaysList(){
        model.daysList.observe(viewLifecycleOwner){ list->
            daysAdapter.submitList(list)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(day: DayModel) {
        val bundle = Bundle().apply {
            putInt("day_id",day.id ?:-1)
            putInt("day_number",day.dayNumber)
        }
         findNavController().navigate(R.id.customExercisesListFragment,bundle)
    }

    override fun onDelete(day: DayModel) {
        DialogManager.showDialog(requireContext(),"Delete day?",object :DialogManager.Listener{
            override fun onClick() {
                model.deleteDay(day)
            }

        })
    }
}