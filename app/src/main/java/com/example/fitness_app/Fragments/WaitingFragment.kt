package com.example.fitness_app.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.fitness_app.databinding.WaitingFragmentBinding
import com.example.fitness_app.utils.ViewModels.MainViewModel
import com.example.fitness_app.utils.Objects.Time


class WaitingFragment : Fragment() {

    private lateinit var binding: WaitingFragmentBinding
    private lateinit var timer: CountDownTimer
    val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = WaitingFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.max = 11000
        StartTimer()


    }
    private fun StartTimer () {
        timer = object : CountDownTimer(11000,100){
            override fun onTick(restTime: Long) {
                binding.tvTimer.text = Time.getTime(restTime)
                binding.progressBar.progress = restTime.toInt()
            }

            override fun onFinish() {

            }
        }.start()
    }


    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }
    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()
    }

}