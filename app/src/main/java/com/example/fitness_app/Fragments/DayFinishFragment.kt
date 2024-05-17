package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.fitness_app.databinding.DayFinishBinding
import com.example.fitness_app.utils.ViewModels.MainViewModel
import pl.droidsonroids.gif.GifDrawable


class DayFinishFragment : Fragment() {

    private lateinit var binding: DayFinishBinding
    private var ab: ActionBar? = null

    val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DayFinishBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab= (activity as AppCompatActivity).supportActionBar
        ab?.title = "Done"
        binding.imgCongrat.setImageDrawable(GifDrawable((activity as AppCompatActivity ).assets,"congrats.gif"))
        binding.btnDone.setOnClickListener{
            //FragmentManager.setFragment(DaysFragment.newInstance(), activity as AppCompatActivity)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = DayFinishFragment()
    }

}