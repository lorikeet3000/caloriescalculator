package ru.caloriescalculator.calories.data.repository

import ru.caloriescalculator.calories.data.dao.CaloriesDao
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import javax.inject.Inject

class CaloriesRepository @Inject constructor(
    private val caloriesDao: CaloriesDao
) {

    suspend fun addCalories(caloriesEntity: CaloriesEntity) {
        caloriesDao.insertAll(caloriesEntity)
    }
}