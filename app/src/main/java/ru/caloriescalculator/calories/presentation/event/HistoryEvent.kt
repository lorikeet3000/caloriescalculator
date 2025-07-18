package ru.caloriescalculator.calories.presentation.event

import ru.caloriescalculator.calories.presentation.model.CaloriesItem

sealed interface HistoryEvent {

    data class OnItemClick(
        val item: CaloriesItem
    ) : HistoryEvent

    data object OnItemBottomSheetClose : HistoryEvent

    data class OnItemDeleteClick(
        val item: CaloriesItem
    ) : HistoryEvent

    data object OnDeleteDialogDismiss : HistoryEvent

    data class OnConfirmDeleteClick(
        val id: Long
    ) : HistoryEvent

    data class OnAddForTodayClick(
        val item: CaloriesItem
    ) : HistoryEvent
}