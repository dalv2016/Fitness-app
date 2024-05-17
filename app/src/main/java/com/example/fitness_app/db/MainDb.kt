package com.example.fitness_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitness_app.intarfaces.Dao.DaysDao
import com.example.fitness_app.intarfaces.Dao.ExerciseDao
import com.example.fitness_app.intarfaces.Dao.StatisticsDao
import com.example.fitness_app.intarfaces.Dao.WeightDao

@Database(
    entities = [
        DayModel::class,
        ExerciseModel::class,
        WeightModel::class,
        StatisticModel::class
               ],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val daysDao: DaysDao
    abstract val exerciseDao: ExerciseDao
    abstract val weightDao: WeightDao
    abstract val statisticsDao: StatisticsDao
}