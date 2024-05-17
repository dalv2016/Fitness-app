package com.example.fitness_app.Fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness_app.Adapters.ExrciseAdapter
import com.example.fitness_app.R
import com.example.fitness_app.databinding.ExercisesListFragmentBinding
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.utils.ViewModels.ExerciseListViewModel
import com.example.fitness_app.utils.getDayFromArguments


class ExercisesListFragment : Fragment() {

    private  var dayModel: DayModel? = null
    private lateinit var binding: ExercisesListFragmentBinding
    private lateinit var adapter: ExrciseAdapter
    val model: ExerciseListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ExercisesListFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayModel = getDayFromArguments()
        init()
        topCardObserver()
        exerciseListObserver()
        dayModel = getDayFromArguments()
        model.getDayExerciseList(dayModel)
        /*model.mutavleListExetces.observe(viewLifecycleOwner){
            adapter.submitList(it)
            for( i in 0 until model.getPref()){
            it[i] = it[i].copy(isDone = true)
        }
        }*/

    }
    private fun init () {
        adapter = ExrciseAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(activity)
        binding.rcView.adapter = adapter
        binding.button.setOnClickListener{
            val bundle = Bundle().apply {
                putSerializable("dau", dayModel)
            }
            findNavController().navigate(R.id.action_exercisesListFragment_to_exerciseFragment, bundle)
        }
    }



    private fun exerciseListObserver(){
        model.exerciseList.observe(viewLifecycleOwner){list ->
            adapter.submitList(list)
        }
    }
    private fun topCardObserver(){
        model.topCardUpdate.observe(viewLifecycleOwner){card ->
            binding.apply {
                val alfaAnimation = AlphaAnimation(0.02f, 1.0f)
                alfaAnimation.duration = 700
                val alfaAnimationText = AlphaAnimation(0.02f, 1.0f)
                alfaAnimationText.duration = 700
                imageView3.setImageResource((card.imageId))
                imageView3.startAnimation(alfaAnimation)
                textView2.setText(card.difficultybyTitle)
                val dayRest = (card.maxprogress - card.progress)
                textView3.text = if(dayRest==0){
                    getString(R.string.Slogan)
                }
                else{
                    dayRest.toString()
                }
                textView3.visibility = View.VISIBLE
                textView2. visibility = View.VISIBLE
                textView2.startAnimation(alfaAnimationText)
                textView3.startAnimation(alfaAnimationText)
                progressBar2.max = card.maxprogress
                animProgressBar(card.progress)
            }
        }
    }
    private fun animProgressBar(progress: Int){
        val anim = ObjectAnimator.ofInt(binding.progressBar2,"progress",binding.progressBar2.progress,progress*100)
        anim.duration = 700
        anim.start()
    }
}