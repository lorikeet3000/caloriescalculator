package ru.caloriescalculator.calories.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.caloriescalculator.calories.presentation.composable.AddCaloriesScreen
import ru.caloriescalculator.calories.presentation.composable.HistoryScreen
import ru.caloriescalculator.calories.presentation.composable.HomeScreen
import ru.caloriescalculator.calories.presentation.composable.ProfileScreen
import ru.caloriescalculator.calories.presentation.composable.ViewDayCaloriesScreen
import ru.caloriescalculator.calories.presentation.viewmodel.AddCaloriesViewModel
import ru.caloriescalculator.calories.presentation.viewmodel.HomeViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            HomeScreen(
                onAddCaloriesClick = {
                    navHostController.navigate(Screen.AddCalories.route)
                },
                uiState = uiState
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }
        composable(Screen.AddCalories.route) {
            val viewModel = hiltViewModel<AddCaloriesViewModel>()
            AddCaloriesScreen(
                foodName = viewModel.foodNameValue,
                caloriesFor100 = viewModel.caloriesFor100Value,
                foodWeight = viewModel.foodWeightValue,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.ViewDayCalories.route) {
            ViewDayCaloriesScreen()
        }
    }
}