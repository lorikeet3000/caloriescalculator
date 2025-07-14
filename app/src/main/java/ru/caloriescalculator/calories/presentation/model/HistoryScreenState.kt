package ru.caloriescalculator.calories.presentation.model

import java.util.Date

data class HistoryScreenState(
    val items: List<DayCalories> = emptyList<DayCalories>(),
    val itemBottomSheet: CaloriesItem? = null,
    val confirmDeleteDialogState: HistoryConfirmDeleteDialogState? = null,
)

data class DayCalories(
    val dayItems: List<CaloriesItem>,
    val date: Date,
) {

    val dayTotalCalories: Int
        get() {
            return dayItems.sumOf {
                it.totalCalories
            }
        }
}