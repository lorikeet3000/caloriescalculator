package ru.caloriescalculator.calories.data

import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.DayCaloriesItem
import java.util.Date

object LocalDataSource {

    val todayCaloriesTest = DayCaloriesItem(
        meals = listOf(
            CaloriesItem(
                date = Date(),
                calories = 300,
                name = "Булочка"
            ),
            CaloriesItem(
                date = Date(),
                calories = 1000,
                name = "Pizza"
            ),
            CaloriesItem(
                date = Date(),
                calories = 600,
                name = "Hot dog"
            ),
        ),
        date = Date()
    )

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