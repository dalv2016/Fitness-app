package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_app.Adapters.CustomExrciseAdapter
import com.example.fitness_app.R
import com.example.fitness_app.ViewModels.CustomExercisesListViewModel
import com.example.fitness_app.databinding.FragmentCustomExerciseListBinding
import com.example.fitness_app.db.ExerciseModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections
@AndroidEntryPoint
class CustomExercisesListFragment : Fragment(),CustomExrciseAdapter.Listener {
    private  var day_id = -1
    private var binding: FragmentCustomExerciseListBinding? = null
    private lateinit var adapter: CustomExrciseAdapter
    private val _binding get() = binding!!
    private val model : CustomExercisesListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomExerciseListBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        updateDay()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRc()
        dayObserver()
        getArgs()
        _binding.btnAddExercise.setOnClickListener{
            val bundle = Bundle().apply {
                putInt("day_id",day_id)

            }
            findNavController().navigate(R.id.chooseExerciseFragment,bundle)
        }
    }
    private fun updateDay(){
        var exercises =""
        adapter.currentList.forEach{
            exercises+=",${it.id}"
        }
        model.updateDay(exercises)
    }
    private fun initRc(){
        _binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = CustomExrciseAdapter(this@CustomExercisesListFragment)
            recyclerView.adapter = adapter
            createItemTouchHelper().attachToRecyclerView(recyclerView)
        }
    }

    private fun dayObserver(){
        model.listExercisesData.observe(viewLifecycleOwner){list  ->
            _binding.textView10.visibility = if(list.isEmpty()){
                View.VISIBLE
            }
            else{
                View.GONE
            }
            val counter = "Count of exercise ${list.size}"
            _binding.tvConter.text = counter
            adapter.submitList(list)
        }
    }
    private fun createItemTouchHelper():ItemTouchHelper{
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,0){
            override fun onMove(
                recyclerView: RecyclerView,
                startItem: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val tmpList = ArrayList<ExerciseModel>(adapter.currentList)
                Collections.swap(tmpList,startItem.adapterPosition,target.adapterPosition)
                adapter.submitList(tmpList)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        })
    }

    private fun getArgs(){
        arguments.apply {
            day_id = this?.getInt("day_id") ?: -1
            val day_number = this?.getInt("day_number") ?: -1
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Day $day_number"
            if(day_id !=-1){
                model.getExercises(day_id)
            }}

    }
    override fun Delete(position: Int) {
        val tmpList = ArrayList<ExerciseModel>(adapter.currentList)
        tmpList.removeAt(position)
        adapter.submitList(tmpList)
        if(tmpList.isEmpty()){
            _binding.textView10.visibility = View.VISIBLE
        }

    }


}