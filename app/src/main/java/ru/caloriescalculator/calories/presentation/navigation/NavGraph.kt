package ru.caloriescalculator.calories.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            AddCaloriesScreen(
                foodName = viewModel.foodNameValue,
                caloriesFor100 = viewModel.caloriesFor100Value,
                foodWeight = viewModel.foodWeightValue,
                isFoodNameError = uiState.value.isFoodNameError,
                isCaloriesError = uiState.value.isCaloriesError,
                isWeightError = uiState.value.isWeightError,
                confirmDialogState = uiState.value.confirmDialogState,
                evaluatedCalories = uiState.value.evaluatedCalories,
                onScreenClose = {
                    navHostController.popBackStack()
                },
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.ViewDayCalories.route) {
            ViewDayCaloriesScreen()
        }
    }
}