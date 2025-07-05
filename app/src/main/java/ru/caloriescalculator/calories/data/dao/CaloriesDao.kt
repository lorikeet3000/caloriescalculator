package ru.caloriescalculator.calories.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.caloriescalculator.calories.data.model.CaloriesEntity

@Dao
interface CaloriesDao {
    @Query("SELECT * FROM calories")
    fun getAll(): Flow<CaloriesEntity>

    @Insert
    suspend fun insertAll(vararg caloriesEntity: CaloriesEntity)
}