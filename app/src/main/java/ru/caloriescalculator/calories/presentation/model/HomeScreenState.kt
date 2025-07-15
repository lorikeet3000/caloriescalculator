package ru.caloriescalculator.calories.presentation.model

import kotlin.math.max

data class HomeScreenState(
    val items: List<CaloriesItem> = emptyList<CaloriesItem>(),
    val date: String = "",
    val todayTotalCalories: Int = 0,
    val itemBottomSheet: CaloriesItem? = null,
    val confirmDeleteDialogState: ConfirmDeleteDialogState? = null,
) {

    val todayCurrentCalories: Int
        get() {
            return items.sumOf {
                it.totalCalories
            }
        }

    val remainingCalories: Int
        get() {
            return max(0, todayTotalCalories - todayCurrentCalories)
        }
}