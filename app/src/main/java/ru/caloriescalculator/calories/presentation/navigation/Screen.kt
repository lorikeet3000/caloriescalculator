package ru.caloriescalculator.calories.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector, val route: String) {
    object Home : Screen(title = "Главная", icon = Icons.Default.Home, route = "home_screen")
    object Profile : Screen(title = "Профиль", icon = Icons.Default.Person, route = "profile_screen")
    object History : Screen(title = "История", icon = Icons.Default.DateRange, route = "history_screen")
    object AddCalories : Screen(title = "Добавить калории", icon = Icons.Default.Home, route = "add_screen")
}