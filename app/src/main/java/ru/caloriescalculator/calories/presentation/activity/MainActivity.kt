package ru.caloriescalculator.calories.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ru.caloriescalculator.calories.presentation.composable.MainScreen
import ru.caloriescalculator.calories.ui.theme.CaloriesCalculatorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaloriesCalculatorTheme {
                MainScreen()
            }
        }
    }
}