package ru.caloriescalculator.calories.data

import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.DayCaloriesItem
import java.util.Date

object LocalDataSource {

    val testCalories = listOf(
        DayCaloriesItem(
            date = Date(),
            meals = listOf(
                CaloriesItem(
                    date = Date(),
                    calories = 1000,
                    name = "Булочка"
                ),
                CaloriesItem(
                    date = Date(),
                    calories = 1000,
                    name = "Pizza"
                )
            ),
        ),
        DayCaloriesItem(
            date = Date(),
            meals = listOf(
                CaloriesItem(
                    date = Date(),
                    calories = 1050,
                    name = "Булочка"
                ),
                CaloriesItem(
                    date = Date(),
                    calories = 1070,
                    name = "Pizza"
                )
            ),
        ),
    )
}