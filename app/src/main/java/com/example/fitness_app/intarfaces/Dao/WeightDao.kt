package com.example.fitness_app.intarfaces.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.db.WeightModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeight(weightModel: WeightModel)

    @Query("Select * From weight_table Where year =:year And month=:month")
    suspend fun getMonthWeightList(year:Int, month: Int):List<WeightModel>

    @Query("Select * From weight_table")
    suspend fun getAllWeightList():List<WeightModel>

    @Query("Select * From weight_table Where year =:year And month=:month And day=:day")
     suspend fun getMonthWeightToday(year:Int, month: Int,day: Int): WeightModel


}