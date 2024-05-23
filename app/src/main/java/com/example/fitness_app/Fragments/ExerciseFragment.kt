package com.example.fitness_app.Fragments

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitness_app.R
import com.example.fitness_app.db.ExerciseModel
import com.example.fitness_app.databinding.ExerciseBinding
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.utils.Objects.FragmentManager
import com.example.fitness_app.utils.Objects.Time
import com.example.fitness_app.utils.ViewModels.ExerciseViewModel
import com.example.fitness_app.utils.getDayFromArguments
import dagger.hilt.android.AndroidEntryPoint
import pl.droidsonroids.gif.GifDrawable

@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    private lateinit var binding : ExerciseBinding

    private var ab: ActionBar? = null
    val model: ExerciseViewModel by viewModels()
    private var currentDay: DayModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ExerciseBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = getDayFromArguments()
        updateExercise()
        updateTime()
        updateToolbar()
        currentDay?.let { model.getExercise(it) }
        ab= (activity as AppCompatActivity).supportActionBar
        binding.btnNext.setOnClickListener{
            if(binding.btnNext.text.toString()=="End Day")
            {
                findNavController().popBackStack(R.id.trainingFragment,false)
            }
            else {
                model.nextExercise()
                binding.progressBar3.max = 0
            }
            }

    }
    private fun changeButtonText(name:String){
        if(name == "Finish"){
            binding.btnNext.text = "End Day"
        }
    }
    private fun updateExercise(){
    model.updateExercise.observe(viewLifecycleOwner){exercise->
        binding.imgCurrentExercise.setImageDrawable(exercise?.image?.let { GifDrawable(binding.root.context.assets, it) })
        binding.tvName.text = exercise?.name
        binding.tvTitle.text = exercise.subTitle
        showTime(exercise)
        setRelax(!binding.tvTitle.text.toString().startsWith("Relax"))
        changeButtonText(exercise.name)
    }
    }
    private fun updateTime() {
        model.updateTime.observe(viewLifecycleOwner){time->
            binding.progressBar3.progress = time.toInt()
            binding.tvTime.text = Time.getTime(time)
            animProgressBar(time)
        }
    }
    private fun updateToolbar(){
        model.updateToolbar.observe(viewLifecycleOwner){text->
            ab?.title = text

        }
    }
    private fun showTime(exercise: ExerciseModel){
        if(exercise.time.startsWith("x") || exercise.time.isEmpty()){
            binding.tvTime.text = exercise.time
        }
        else{
            binding.progressBar3.max = exercise.time.toInt() * 1000
            model.startTimer(exercise.time.toLong())
        }
    }
    private fun setRelax(isExercise: Boolean){
        val white = ContextCompat.getColor(requireContext(), R.color.white)
        val blue = ContextCompat.getColor(requireContext(), R.color.purple_700)
        val black = ContextCompat.getColor(requireContext(), R.color.black)
        val purple = ContextCompat.getColor(requireContext(), R.color.purple_500)
        if(isExercise){
            binding.layout.setBackgroundColor(white)
            binding.tvName.setTextColor(black)
            binding.tvTitle.setTextColor(purple)
            binding.tvTime.setTextColor(black)

            binding.progressBar3.progressTintList = ColorStateList.valueOf(purple)
            binding.progressBar3.backgroundTintList = ColorStateList.valueOf(white)
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(blue)
            binding.btnNext.setTextColor(white)
        }
        else{
            binding.layout.setBackgroundColor(blue)
            binding.tvName.setTextColor(white)
            binding.tvTitle.setTextColor(white)
            binding.tvTime.setTextColor(white)

            binding.progressBar3.progressTintList = ColorStateList.valueOf(white)
            binding.progressBar3.backgroundTintList = ColorStateList.valueOf(white)
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(black)
            binding.btnNext.setTextColor(white)
        }
    }
    private fun animProgressBar(restTime: Long){
        val progressTo = if(restTime>1000){
        restTime-1000
        }
        else{
            0
        }
        val anim = ObjectAnimator.ofInt(binding.progressBar3,"progress",binding.progressBar3.progress,progressTo.toInt())
        anim.duration = 700
        anim.start()
    }
    override fun onPause() {
        super.onPause()
        model.onPause()
    }
   /*   private var exList: ArrayList<ExerciseModel>? = null
        private var exerciseCounter = 0

     private fun nextExercise(){
        if (exerciseCounter<exList?.size!!){
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            showTime(ex)
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
           // binding.imgNextExercise.setImageDrawable(GifDrawable(binding.root.context.assets,ex.image))
        }
        else{
           // binding.imgNextExercise.setImageDrawable(GifDrawable(binding.root.context.assets,"congrats.gif"))
            binding.tvName.text = "Done"
        }
    }
   private fun showExercise(exercise: ExerciseModel?) {

        binding.imgCurrentExercise.setImageDrawable(exercise?.image?.let { GifDrawable(binding.root.context.assets, it) })
        binding.tvName.text = exercise?.name
        val title = "$exerciseCounter / ${exList?.size}"
        ab?.title = title
    }

    private fun setTimeType(exercise: ExerciseModel){
        if(exercise.time.startsWith("x")){
            binding.tvNextName.text = exercise.time
        }
        else{
            val name = exercise.name +": ${Time.getTime((exercise.time.toLong()*1000))}"
            binding.tvNextName.text = name
        }
    }*/

}