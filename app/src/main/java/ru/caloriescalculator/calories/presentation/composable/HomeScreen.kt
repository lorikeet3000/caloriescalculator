package ru.caloriescalculator.calories.presentation.composable

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.caloriescalculator.calories.presentation.model.DayCaloriesItem
import ru.caloriescalculator.calories.presentation.navigation.Screen
import ru.caloriescalculator.calories.presentation.viewmodel.HomeViewModel
import ru.caloriescalculator.calories.ui.theme.DayCaloriesHeaderBackground

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(navController: NavController, homeScreenViewModel: HomeViewModel = hiltViewModel()) {
    val uiState by homeScreenViewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddCalories.route)
                },
            ) {
                Icon(Icons.Filled.Add, "Добавить калории")
            }
        },
    ) { _ ->
        Column(
            modifier = Modifier,
        ) {
            // todo add possibility to show 30 days or 1 day
            AverageCaloriesView(
                averageCalories = uiState.getAverageCalories(7)
            )
            CaloriesListView(
                calories = uiState.calories,
                onHeaderClick = {
                    // todo expand/collapse
                }
            )
        }
    }
}

@Composable
private fun AverageCaloriesView(
    averageCalories: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 48.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "$averageCalories ккал",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Среднее количество калорий за неделю"
        )
    }
}

@Composable
private fun CaloriesListView(
    calories: List<DayCaloriesItem>,
    onHeaderClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 140.dp),
        content = {
            calories.forEach { caloriesItem ->
                DaySection(
                    sectionData = caloriesItem,
                    onHeaderClick = onHeaderClick
                )
            }
        }
    )
}

@Composable
private fun HeaderItemView(
    date: String,
    dayCalories: String,
) {
    Row(modifier = Modifier.fillMaxWidth()
        .background(color = DayCaloriesHeaderBackground)) {
        Text(
            text = date,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp),
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = dayCalories,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
private fun DayCaloriesItemView(
    foodName: String,
    foodCalories: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = foodName,
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp),
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = foodCalories,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp),
        )
    }
}

fun LazyListScope.DaySection(
    sectionData: DayCaloriesItem,
    onHeaderClick: () -> Unit
) {
    item {
        HeaderItemView(
            date = DateFormat.format("dd MMM", sectionData.date).toString(),
            dayCalories = sectionData.dayCalories.toString(),
        )
    }

    items(sectionData.meals) { item ->
        DayCaloriesItemView(
            foodName = item.name,
            foodCalories = item.calories.toString()
        )
    }
}