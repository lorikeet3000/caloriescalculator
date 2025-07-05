package ru.caloriescalculator.calories.data.repository

import ru.caloriescalculator.calories.data.dao.CaloriesDao
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import javax.inject.Inject

class CaloriesRepository @Inject constructor(
    private val caloriesDao: CaloriesDao
) {

    suspend fun addCalories(caloriesItem: CaloriesItem) {
        val caloriesEntity = CaloriesEntity(
            date = caloriesItem.date.time,
            foodName = caloriesItem.name,
            calories = caloriesItem.calories
        )
        caloriesDao.insertAll(caloriesEntity)
    }
}