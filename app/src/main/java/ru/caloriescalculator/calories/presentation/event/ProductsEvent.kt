package ru.caloriescalculator.calories.presentation.event

import ru.caloriescalculator.calories.presentation.model.Product

sealed interface ProductsEvent {

    data class OnProductClick(
        val product: Product,
    ) : ProductsEvent

    data object OnBottomSheetDismissClick : ProductsEvent

    data class OnAddForTodayClick(
        val product: Product
    ) : ProductsEvent

    data class OnProductDeleteClick(
        val product: Product
    ) : ProductsEvent

    data object OnDeleteDialogDismiss : ProductsEvent

    data class OnConfirmDeleteClick(
        val id: Long
    ) : ProductsEvent
}