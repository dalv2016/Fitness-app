package com.example.fitness_app.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitness_app.Fragments.DaysFragment
import com.example.fitness_app.utils.Objects.TrainingUtils

class VpAdapter(fragment: Fragment, private val custom: Int ):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return TrainingUtils.difListType.size - custom
    }

    override fun createFragment(position: Int): Fragment {
            return DaysFragment.newInstance()
    }
}