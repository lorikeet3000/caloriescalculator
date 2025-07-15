package ru.caloriescalculator.calories.presentation.event

import ru.caloriescalculator.calories.presentation.model.CaloriesItem

sealed interface HomeEvent {

    data class OnItemClick(
        val item: CaloriesItem
    ) : HomeEvent

    data object OnItemBottomSheetClose : HomeEvent

    data class OnItemEditClick(
        val item: CaloriesItem
    ) : HomeEvent

    data class OnItemDeleteClick(
        val item: CaloriesItem
    ) : HomeEvent

    data object OnDeleteDialogDismiss : HomeEvent

    data class OnConfirmDeleteClick(
        val item: CaloriesItem
    ) : HomeEvent

    data class OnAddForTodayClick(
        val item: CaloriesItem
    ) : HomeEvent

    data object OnPullToRefresh : HomeEvent
}