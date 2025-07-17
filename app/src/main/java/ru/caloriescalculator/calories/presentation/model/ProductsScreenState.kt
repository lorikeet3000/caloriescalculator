package ru.caloriescalculator.calories.presentation.model

import ru.caloriescalculator.calories.core.CaloriesCalculator

data class ProductsScreenState(
    val products: List<Product> = emptyList<Product>()
)

data class Product(
    val name: String,
    val caloriesFor100: Int,
    val weight: Int,
    val comment: String,
    val id: Long = 0
) {

    val totalCalories: Int
        get() {
            return CaloriesCalculator.calculateTotalCalories(
                caloriesFor100 = caloriesFor100,
                weight = weight,
            )
        }
}