package com.example.fitness_app.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitness_app.Fragments.DaysFragment
import com.example.fitness_app.utils.TrainingUtils

class VpAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TrainingUtils.difListType.size
    }

    override fun createFragment(position: Int): Fragment {
            return DaysFragment.newInstance()
    }
}