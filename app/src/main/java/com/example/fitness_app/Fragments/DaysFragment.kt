package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitness_app.R
import com.example.fitness_app.databinding.FragmentDaysBinding
import com.example.fitness_app.utils.FragmentManager

class DaysFragment : Fragment() {

    private lateinit var binding: FragmentDaysBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}