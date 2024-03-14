package com.example.fitness_app.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.fitness_app.R
import org.w3c.dom.DocumentFragment

object FragmentManager {
    var currentFrafment : Fragment ?= null
    fun setFragment(newFragment: Fragment, act: AppCompatActivity){
        val transaction = act.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFragment)
        transaction.commit()
        currentFrafment = newFragment
    }
}