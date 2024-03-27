package com.example.fitness_app.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness_app.Models.DataClass.ExerciseModel
import com.example.fitness_app.Models.ExerciseAdapter.ExrciseAdapter
import com.example.fitness_app.databinding.ExerciseBinding
import com.example.fitness_app.databinding.ExercisesListFragmentBinding
import com.example.fitness_app.databinding.WaitingFragmentBinding
import com.example.fitness_app.utils.FragmentManager
import com.example.fitness_app.utils.MainViewModel
import com.example.fitness_app.utils.Time
import pl.droidsonroids.gif.GifDrawable


class ExerciseFragment : Fragment() {

    private lateinit var binding : ExerciseBinding
    private var timer: CountDownTimer? = null
    val model: MainViewModel by activityViewModels()
    private var exList: ArrayList<ExerciseModel>? = null
    private var exerciseCounter = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ExerciseBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.mutavleListExetces.observe(viewLifecycleOwner){
            exList = it
          nextExercise()
        }
        binding.btnNext.setOnClickListener{
            nextExercise()
        }
    }

    private fun nextExercise(){
        if (exerciseCounter<exList?.size!!){
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            setExersiceType(ex)
        }
        else{
            Toast.makeText(activity,"Done",Toast.LENGTH_LONG).show()
        }
    }

    private fun showExercise(exercise: ExerciseModel?) {

        binding.imgCurrentExercise.setImageDrawable(exercise?.image?.let { GifDrawable(binding.root.context.assets, it) })
        binding.tvName.text = exercise?.name

    }

    private fun setExersiceType(exercise: ExerciseModel){
        if(exercise.time.startsWith("x")){
            binding.tvTime.text = exercise.time
        }
        else{
        StartTimer(exercise)
        }
    }

    private fun StartTimer (exercise: ExerciseModel) {
        timer = object : CountDownTimer(11000,100){
            override fun onTick(restTime: Long) {
                binding.tvTime.text = Time.getTime(restTime)
                binding.progressBar3.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }
    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }

}