package ru.caloriescalculator.calories.presentation.event

import ru.caloriescalculator.calories.presentation.model.Product

sealed interface ProductsEvent {

    data class OnProductClick(
        val product: Product,
    ) : ProductsEvent
}