package ru.caloriescalculator.calories.presentation.event

import ru.caloriescalculator.calories.presentation.model.CaloriesItem

sealed interface HistoryEvent {

    data class OnItemClick(
        val item: CaloriesItem
    ) : HistoryEvent

    data object OnItemBottomSheetClose : HistoryEvent

    data class OnItemEditClick(
        val item: CaloriesItem
    ) : HistoryEvent

    data class OnItemDeleteClick(
        val item: CaloriesItem
    ) : HistoryEvent

    data object OnDeleteDialogDismiss : HistoryEvent

    data class OnConfirmDeleteClick(
        val item: CaloriesItem
    ) : HistoryEvent
}