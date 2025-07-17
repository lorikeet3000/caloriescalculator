package ru.caloriescalculator.calories.presentation.effect

sealed interface ProductEffect {

    data object CloseScreen : ProductEffect
}