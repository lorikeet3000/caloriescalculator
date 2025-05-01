package ru.caloriescalculator.calories.presentation.composable

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import ru.caloriescalculator.calories.presentation.model.DayCaloriesItem
import ru.caloriescalculator.calories.presentation.navigation.Screen
import ru.caloriescalculator.calories.presentation.viewmodel.HomeViewModel

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
    ) {  _ ->
        Column(
            modifier = Modifier,
        ) {
            LineChartView(uiState.calories)
            CaloriesListView(navController, uiState.calories)
        }
    }
}

@Composable
private fun LineChartView(caloriesItems: List<DayCaloriesItem>) {
    val calories = caloriesItems.map {
        it.dayCalories.toDouble()
    }
    val dates = caloriesItems.map {
        DateFormat.format("dd", it.date).toString()
    }
    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "Calories",
            data = calories,
            lineColor = Color.Blue,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true,
        ),
    )
    LineChart(
        modifier = Modifier.height(height = 300.dp),
        linesParameters = lineParameters,
        isGrid = true,
        gridColor = Color.Gray,
        xAxisData = dates,
        animateChart = false,
        showGridWithSpacer = true,
        yAxisStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Gray,
        ),
        xAxisStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.W400
        ),
        yAxisRange = 10,
        oneLineChart = false,
        gridOrientation = GridOrientation.VERTICAL,
        legendPosition = LegendPosition.DISAPPEAR
    )
}

@Composable
private fun CaloriesListView(navController: NavController, calories: List<DayCaloriesItem>) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(items = calories) { item ->
            val date = DateFormat.format("dd MMM", item.date).toString()
            OutlinedCard(
                onClick = {
                    navController.navigate(Screen.ViewDayCalories.route)
                },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = date,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp),
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = item.dayCalories.toString(),
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}