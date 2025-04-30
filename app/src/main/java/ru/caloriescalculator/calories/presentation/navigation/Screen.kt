package ru.caloriescalculator.calories.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector, val route: String) {
    object Home: Screen(title = "Home", icon = Icons.Default.Home, route = "home_screen")
    object Profile: Screen(title = "Profile", icon = Icons.Default.Person, route = "profile_screen")
    object History: Screen(title = "History", icon = Icons.Default.DateRange, route = "history_screen")
}