package ru.caloriescalculator.calories.presentation.composable

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.HomeScreenState
import java.util.Date

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(
    onAddCaloriesClick: () -> Unit,
    uiState: HomeScreenState
) {
    HomeScreen(
        onAddCaloriesClick = onAddCaloriesClick,
        caloriesList = uiState.items,
        todayCurrentCalories = uiState.dayCalories,
        todayTotalCalories = uiState.todayTotalCalories,
        remainingCalories = uiState.remainingCalories
    )
}

@Composable
private fun HomeScreen(
    onAddCaloriesClick: () -> Unit = {},
    caloriesList: List<CaloriesItem> = emptyList<CaloriesItem>(),
    todayCurrentCalories: Int = 0,
    todayTotalCalories: Int = 0,
    remainingCalories: Int = 0
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCaloriesClick,
            ) {
                Icon(Icons.Filled.Add, "Добавить калории")
            }
        },
    ) { _ ->
        Column(
            modifier = Modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                CurrentTodayCaloriesView(
                    todayCurrentCalories = todayCurrentCalories
                )
                if (todayTotalCalories > 0) {
                    TotalTodayCaloriesView(
                        todayTotalCalories = todayTotalCalories
                    )
                }
            }
            RemainingCaloriesView(
                modifier = Modifier.padding(bottom = 50.dp),
                remainingCalories = remainingCalories
            )
            TodayHeaderView()
            HorizontalDivider(
                thickness = 2.dp
            )
            TodayCaloriesListView(
                calories = caloriesList
            )
        }
    }
}

@Composable
private fun CurrentTodayCaloriesView(
    todayCurrentCalories: Int
) {
    Text(
        text = "$todayCurrentCalories ккал",
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun TotalTodayCaloriesView(
    todayTotalCalories: Int
) {
    Text(
        text = "/$todayTotalCalories ккал",
        fontSize = 30.sp,
    )
}

@Composable
private fun RemainingCaloriesView(
    modifier: Modifier,
    remainingCalories: Int
) {
    Text(
        text = "Осталось $remainingCalories ккал",
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TodayHeaderView() {
    val date = DateFormat.format("dd MMM", Date()).toString()
    Text(
        text = "Сегодня, $date",
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Composable
private fun TodayCaloriesListView(
    calories: List<CaloriesItem>,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 140.dp),
    ) {
        items(calories) { item ->
            CaloriesItemView(
                item = item
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun CaloriesItemView(
    item: CaloriesItem
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            Text(
                text = item.foodName,
                fontSize = 16.sp,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "${item.totalCalories} ккал",
                fontSize = 22.sp,
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(end = 4.dp),
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            text = "на 100г/мл: ${item.caloriesFor100} ккал, вес: ${item.weight}"
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        caloriesList = listOf(
            CaloriesItem(
                date = Date(),
                caloriesFor100 = 100,
                foodName = "Овощи гриль",
                weight = 400
            )
        ),
        todayCurrentCalories = 1234,
        todayTotalCalories = 1300,
        remainingCalories = 200
    )
}