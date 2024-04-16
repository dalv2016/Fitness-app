package com.example.fitness_app.intarfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_app.db.StatisticModel
import java.util.Date

@Dao
interface StatisticsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayStatistic(statisticModel: StatisticModel)
    @Query("Select * From statistic_table")
    suspend fun getStatistics(): List<StatisticModel>

    @Query("Select * From statistic_table where date =:date ")
    suspend fun getStatisticByDate(date: String): StatisticModel
}