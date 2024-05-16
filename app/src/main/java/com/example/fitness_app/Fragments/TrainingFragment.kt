package com.example.fitness_app.Fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.fitness_app.databinding.FragmentTrainingBinding
import com.example.fitness_app.utils.TrainingUtils
import com.example.fitness_app.Adapters.VpAdapter
import com.example.fitness_app.utils.DaysViewModel
import com.google.android.material.tabs.TabLayoutMediator

class TrainingFragment : Fragment() {
    private lateinit var binding: FragmentTrainingBinding
    val model: DaysViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vpAdapter = VpAdapter(this)
        topCardObserver()
        binding.vp2.adapter = vpAdapter
        TabLayoutMediator(binding.tabLayout,binding.vp2){ tab,pos ->
            tab.text = getString(TrainingUtils.tabTitles[pos])

          }.attach()
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                    model.getExerciseDayByDifficulty(TrainingUtils.topCardList[position])
            }
        })
    }
    private fun topCardObserver() = with(binding){
        model.topCardUpdate.observe(viewLifecycleOwner){card ->
            val alfaAnimation = AlphaAnimation(0.02f, 1.0f)
            alfaAnimation.duration = 700
            imageView3.setImageResource(card.imageId)
            imageView3.startAnimation(alfaAnimation)
            textView.setText(card.difficultybyTitle)
            progressBar2.max = card.maxprogress
            animProgressBar(card.progress)
            textView2.text = (card.maxprogress - card.progress).toString()
        }
    }
    private fun animProgressBar(progress: Int){
        val anim = ObjectAnimator.ofInt(binding.progressBar2,"progress",binding.progressBar2.progress,progress*100)
        anim.duration = 700
        anim.start()
    }
}