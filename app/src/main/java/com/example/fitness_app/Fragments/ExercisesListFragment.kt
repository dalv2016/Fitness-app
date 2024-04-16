package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness_app.Adapters.ExerciseAdapter.ExrciseAdapter
import com.example.fitness_app.databinding.ExercisesListFragmentBinding
import com.example.fitness_app.utils.FragmentManager
import com.example.fitness_app.utils.MainViewModel


class ExercisesListFragment : Fragment() {

    private lateinit var binding: ExercisesListFragmentBinding
    private lateinit var adapter: ExrciseAdapter
    val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ExercisesListFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.mutavleListExetces.observe(viewLifecycleOwner){
            adapter.submitList(it)
            for( i in 0 until model.getPref()){
            it[i] = it[i].copy(isDone = true)
        }
        }

    }
    private fun init () {
        adapter = ExrciseAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(activity)
        binding.rcView.adapter = adapter
        binding.button.setOnClickListener{
            FragmentManager.setFragment(WaitingFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesListFragment()
    }

}