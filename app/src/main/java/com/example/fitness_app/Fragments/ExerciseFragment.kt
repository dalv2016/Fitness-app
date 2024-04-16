package com.example.fitness_app.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.databinding.ExerciseBinding
import com.example.fitness_app.utils.FragmentManager
import com.example.fitness_app.utils.MainViewModel
import com.example.fitness_app.utils.Time
import pl.droidsonroids.gif.GifDrawable


class ExerciseFragment : Fragment() {

    private lateinit var binding : ExerciseBinding
    private var timer: CountDownTimer? = null
    private var ab: ActionBar? = null
    val model: MainViewModel by activityViewModels()
    private var exList: ArrayList<ExerciseModel>? = null
    private var exerciseCounter = 0
    private var currentDay = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ExerciseBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = model.currentDay
        exerciseCounter = model.getPref()
        ab= (activity as AppCompatActivity).supportActionBar
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
            showNextExercise()
            setTimeType(ex)
        }
        else{
            exerciseCounter++;
            FragmentManager.setFragment(DayFinishFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    private fun showNextExercise(){
        if (exerciseCounter<exList?.size!!){
            val ex = exList?.get(exerciseCounter) ?: return
            binding.imgNextExercise.setImageDrawable(GifDrawable(binding.root.context.assets,ex.image))
        }
        else{
            binding.imgNextExercise.setImageDrawable(GifDrawable(binding.root.context.assets,"congrats.gif"))
            binding.tvName.text = "Done"
        }
    }
    private fun showExercise(exercise: ExerciseModel?) {

        binding.imgCurrentExercise.setImageDrawable(exercise?.image?.let { GifDrawable(binding.root.context.assets, it) })
        binding.tvName.text = exercise?.name
        val title = "$exerciseCounter / ${exList?.size}"
        ab?.title = title
    }

    private fun setExersiceType(exercise: ExerciseModel){

        if(exercise.time.startsWith("x")){
            timer?.cancel()
            binding.tvTime.text = exercise.time
            binding.progressBar3.visibility = View.GONE
        }
        else{
            binding.progressBar3.visibility = View.VISIBLE
            StartTimer(exercise)
        }
    }

    private fun setTimeType(exercise: ExerciseModel){
        if(exercise.time.startsWith("x")){
            binding.tvNextName.text = exercise.time
        }
        else{
            val name = exercise.name +": ${Time.getTime((exercise.time.toLong()*1000))}"
            binding.tvNextName.text = name
        }
    }
    private fun StartTimer (exercise: ExerciseModel) {
        binding.progressBar3.max = exercise.time.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(exercise.time.toLong()*1000,100){
            override fun onTick(restTime: Long) {
                binding.tvTime.text = Time.getTime(restTime)
                binding.progressBar3.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }
    override fun onDetach() {
        super.onDetach()
        model.savePref(currentDay.toString(),exerciseCounter-1)
        timer?.cancel()
    }
    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }

}