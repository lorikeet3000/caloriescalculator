package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ru.caloriescalculator.calories.presentation.navigation.BottomNavBar
import ru.caloriescalculator.calories.presentation.navigation.NavGraph
import ru.caloriescalculator.calories.presentation.navigation.Screen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddCalories.route)
                },
            ) {
                Icon(Icons.Filled.Add, "Добавить калории")
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navHostController = navController)
        }
    }
}