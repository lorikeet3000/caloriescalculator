package ru.caloriescalculator.calories.data.repository

import kotlinx.coroutines.flow.Flow
import ru.caloriescalculator.calories.data.dao.CaloriesDao
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import javax.inject.Inject

class CaloriesRepository @Inject constructor(
    private val caloriesDao: CaloriesDao
) {

    suspend fun addCalories(caloriesEntity: CaloriesEntity) {
        caloriesDao.insertAll(caloriesEntity)
    }

    fun getForDate(date: String): Flow<List<CaloriesEntity>> {
        return caloriesDao.getForDate(date)
    }
}