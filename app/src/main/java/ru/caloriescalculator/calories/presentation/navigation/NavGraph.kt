package ru.caloriescalculator.calories.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.caloriescalculator.calories.presentation.composable.AddCaloriesScreen
import ru.caloriescalculator.calories.presentation.composable.AddProductScreen
import ru.caloriescalculator.calories.presentation.composable.HistoryScreen
import ru.caloriescalculator.calories.presentation.composable.HomeScreen
import ru.caloriescalculator.calories.presentation.composable.ProductsScreen
import ru.caloriescalculator.calories.presentation.composable.ProfileScreen
import ru.caloriescalculator.calories.presentation.effect.ProductEffect
import ru.caloriescalculator.calories.presentation.viewmodel.AddCaloriesViewModel
import ru.caloriescalculator.calories.presentation.viewmodel.AddProductViewModel
import ru.caloriescalculator.calories.presentation.viewmodel.HistoryViewModel
import ru.caloriescalculator.calories.presentation.viewmodel.HomeViewModel
import ru.caloriescalculator.calories.presentation.viewmodel.ProductsViewModel
import ru.caloriescalculator.calories.presentation.viewmodel.ProfileViewModel

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
            val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
            HomeScreen(
                onAddCaloriesClick = {
                    navHostController.navigate(Screen.AddCalories.route)
                },
                uiState = uiState,
                isRefreshing = isRefreshing,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.Profile.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            ProfileScreen(
                uiState = uiState.value,
                totalCaloriesValue = viewModel.totalCaloriesValue,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.History.route) {
            val viewModel = hiltViewModel<HistoryViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            HistoryScreen(
                uiState = uiState.value,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.AddCalories.route) {
            val viewModel = hiltViewModel<AddCaloriesViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            AddCaloriesScreen(
                foodName = viewModel.foodNameValue,
                caloriesFor100 = viewModel.caloriesFor100Value,
                foodWeight = viewModel.foodWeightValue,
                uiState = uiState.value,
                onScreenClose = {
                    navHostController.popBackStack()
                },
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.Products.route) {
            val viewModel = hiltViewModel<ProductsViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            ProductsScreen(
                uiState = uiState.value,
                onAddProductClick = {
                    navHostController.navigate(Screen.AddProduct.route)
                },
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.AddProduct.route) {
            val viewModel = hiltViewModel<AddProductViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val lifecycleOwner = LocalLifecycleOwner.current
            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.effectsSharedFlow.collect { event ->
                        when (event) {
                            ProductEffect.CloseScreen -> {
                                navHostController.popBackStack()
                            }
                        }
                    }
                }
            }
            AddProductScreen(
                foodName = viewModel.foodNameValue,
                caloriesFor100 = viewModel.caloriesFor100Value,
                foodWeight = viewModel.foodWeightValue,
                comment = viewModel.commentValue,
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                onScreenClose = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}