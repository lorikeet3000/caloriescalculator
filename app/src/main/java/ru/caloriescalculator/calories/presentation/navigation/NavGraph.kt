package ru.caloriescalculator.calories.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.caloriescalculator.calories.presentation.composable.AddCaloriesScreen
import ru.caloriescalculator.calories.presentation.composable.HistoryScreen
import ru.caloriescalculator.calories.presentation.composable.HomeScreen
import ru.caloriescalculator.calories.presentation.composable.ProfileScreen
import ru.caloriescalculator.calories.presentation.composable.ViewDayCaloriesScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navHostController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }
        composable(Screen.AddCalories.route) {
            AddCaloriesScreen(navController = navHostController)
        }
        composable(Screen.ViewDayCalories.route) {
            ViewDayCaloriesScreen()
        }
    }
}