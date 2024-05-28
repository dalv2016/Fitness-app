package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness_app.Adapters.ShooseExrciseAdapter
import com.example.fitness_app.R
import com.example.fitness_app.ViewModels.ChooseExerciseViewModel
import com.example.fitness_app.databinding.FragmentChooseExerciseBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseExerciseFragment : Fragment(), ShooseExrciseAdapter.Listener {

    private var binding: FragmentChooseExerciseBinding? = null
    private  var newExercises = ""
    private val _binding get() = binding!!
    private  lateinit var adapter: ShooseExrciseAdapter
    private val model:  ChooseExerciseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseExerciseBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Exercises list"
        _binding.button2.setOnClickListener{
            model.updateDayById(newExercises)
            findNavController().popBackStack()
        }
        getArgs()
        initRc()
        exerciseListObserver()
        model.getAllExercises()
    }
    private fun getArgs(){
        arguments.apply {
            val day_id = this?.getInt("day_id") ?: -1
            if(day_id !=-1){
                model.getDayById( day_id)
            }}

    }
    private fun initRc(){
        _binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        adapter = ShooseExrciseAdapter(this@ChooseExerciseFragment )
        _binding.recyclerView2.adapter = adapter
    }
    private fun exerciseListObserver(){
        model.exercisesListData.observe(viewLifecycleOwner){list->
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun Click(id: Int) {
        if(id!= -1) {
            newExercises += ",$id"
        }
        val counter = newExercises.split(",").size-1
        _binding.tvExerciseCounter.text= "Selected exercise : $counter"
        Snackbar.make(_binding.recyclerView2, "Exercisewas added",Snackbar.LENGTH_SHORT)
    }

}