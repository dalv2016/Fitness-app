package com.example.fitness_app.utils.Objects

import com.example.fitness_app.R

object TrainingUtils {
    const val EASY = "easy"
    const val MIDDLE = "middle"
    const val HARD = "hard"
    const val CUSTOM = "custom"

    val difListType = listOf(
        EASY,
        MIDDLE,
        HARD,
        CUSTOM
    )
    val tabTitles = listOf(
        R.string.easy,
        R.string.middle,
        R.string.hard,
        R.string.custom
    )

    val topCardList = listOf(
        TrainingTopCardModel(
            R.drawable.arms,
            R.string.easy,
            0,
            0,
            "easy",),
        TrainingTopCardModel(
            R.drawable.legs,
            R.string.middle,
            0,
            0,
            "middle",),
        TrainingTopCardModel(
            R.drawable.press,
            R.string.hard,
            0,
            0,
            "hard",),
        TrainingTopCardModel(
            R.drawable.press,
            R.string.custom,
            0,
            0,
            "custom",),
    )
}