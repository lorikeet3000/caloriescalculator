package ru.caloriescalculator.calories.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.event.HomeEvent
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.HomeScreenState
import java.util.Date

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(
    uiState: HomeScreenState,
    isRefreshing: Boolean = false,
    onAddCaloriesClick: () -> Unit = {},
    onEvent: (HomeEvent) -> Unit = {}
) {
    if (uiState.itemBottomSheet != null) {
        ItemBottomSheetView(
            item = uiState.itemBottomSheet,
            onBottomSheetDismiss = {
                onEvent(HomeEvent.OnItemBottomSheetClose)
            },
            onAddForTodayClick = {
                onEvent(HomeEvent.OnAddForTodayClick(it))
            },
            onDeleteClick = {
                onEvent(HomeEvent.OnItemDeleteClick(it))
            }
        )
    }
    if (uiState.confirmDeleteDialogState != null) {
        ConfirmDeleteDialogView(
            state = uiState.confirmDeleteDialogState,
            onDismissClick = {
                onEvent(HomeEvent.OnDeleteDialogDismiss)
            },
            onConfirmDeleteClick = {
                onEvent(HomeEvent.OnConfirmDeleteClick(it))
            }
        )
    }
    HomeScreenContent(
        uiState = uiState,
        isRefreshing = isRefreshing,
        onAddCaloriesClick = onAddCaloriesClick,
        onItemClick = {
            onEvent(HomeEvent.OnItemClick(it))
        },
        onRefresh = {
            onEvent(HomeEvent.OnPullToRefresh)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeScreenState,
    isRefreshing: Boolean,
    onAddCaloriesClick: () -> Unit,
    onItemClick: (CaloriesItem) -> Unit,
    onRefresh: () -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CurrentTodayCaloriesView(
                todayCurrentCalories = uiState.todayCurrentCalories
            )
            if (uiState.todayTotalCalories > 0) {
                TotalTodayCaloriesView(
                    todayTotalCalories = uiState.todayTotalCalories
                )
            }
            RemainingCaloriesView(
                modifier = Modifier.padding(bottom = 24.dp),
                remainingCalories = uiState.remainingCalories
            )
            AddCaloriesView(
                modifier = Modifier.padding(bottom = 24.dp),
                onAddCaloriesClick = onAddCaloriesClick,
            )
            TodayHeaderView(
                todayDate = uiState.date
            )
            HorizontalDivider(thickness = 2.dp)
            TodayCaloriesListView(
                calories = uiState.items,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun AddCaloriesView(
    modifier: Modifier,
    onAddCaloriesClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onAddCaloriesClick,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Add, "Добавить калории")
                Spacer(modifier = Modifier.size(8.dp))
                Text("Добавить")
            }
        }
    }
}

@Composable
private fun CurrentTodayCaloriesView(
    todayCurrentCalories: Int
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        textAlign = TextAlign.Center,
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
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Цель: $todayTotalCalories ккал",
    )
}

@Composable
private fun RemainingCaloriesView(
    modifier: Modifier,
    remainingCalories: Int
) {
    Text(
        text = "Осталось: $remainingCalories ккал",
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TodayHeaderView(
    todayDate: String
) {
    Text(
        text = "Сегодня, $todayDate",
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Composable
private fun TodayCaloriesListView(
    calories: List<CaloriesItem>,
    onItemClick: (CaloriesItem) -> Unit
) {
    LazyColumn {
        items(calories) { item ->
            CaloriesItemView(
                item = item,
                onClick = onItemClick
            )
            HorizontalDivider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemBottomSheetView(
    item: CaloriesItem,
    onBottomSheetDismiss: () -> Unit,
    onAddForTodayClick: (CaloriesItem) -> Unit,
    onDeleteClick: (CaloriesItem) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onBottomSheetDismiss
    ) {
        ItemBottomSheetContentView(
            item = item,
            onAddForTodayClick = onAddForTodayClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeScreenState(
            items = listOf(
                CaloriesItem(
                    date = Date(),
                    caloriesFor100 = 100,
                    foodName = "Овощи гриль",
                    weight = 400
                )
            ),
            todayTotalCalories = 1300,
        ),
    )
}