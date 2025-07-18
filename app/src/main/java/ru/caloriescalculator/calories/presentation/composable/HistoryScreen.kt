package ru.caloriescalculator.calories.presentation.composable

import android.text.format.DateFormat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.event.HistoryEvent
import ru.caloriescalculator.calories.presentation.event.HistoryEvent.OnDeleteDialogDismiss
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.DayCalories
import ru.caloriescalculator.calories.presentation.model.HistoryScreenState
import java.util.Date

@Composable
fun HistoryScreen(
    uiState: HistoryScreenState,
    onEvent: (HistoryEvent) -> Unit
) {
    if (uiState.itemBottomSheet != null) {
        ItemBottomSheetView(
            item = uiState.itemBottomSheet,
            onBottomSheetDismiss = {
                onEvent(HistoryEvent.OnItemBottomSheetClose)
            },
            onAddForTodayClick = {
                onEvent(HistoryEvent.OnAddForTodayClick(it))
            },
            onDeleteClick = {
                onEvent(HistoryEvent.OnItemDeleteClick(it))
            }
        )
    }
    if (uiState.confirmDeleteDialogState != null) {
        ConfirmDeleteDialogView(
            state = uiState.confirmDeleteDialogState,
            onDismissClick = {
                onEvent(OnDeleteDialogDismiss)
            },
            onConfirmDeleteClick = {
                onEvent(HistoryEvent.OnConfirmDeleteClick(it))
            }
        )
    }
    if (uiState.items.isEmpty()) {
        Text(
            text = "История пуста",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
    } else {
        ItemsListView(
            uiState = uiState,
            onItemClick = {
                onEvent(HistoryEvent.OnItemClick(it))
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemsListView(
    uiState: HistoryScreenState,
    onItemClick: (CaloriesItem) -> Unit,
) {
    LazyColumn {
        uiState.items.forEach { dayItem ->
            stickyHeader {
                DayItem(dayItem)
            }
            items(dayItem.dayItems) { caloriesItem ->
                CaloriesItemView(
                    item = caloriesItem,
                    onClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun DayItem(dayItem: DayCalories) {
    val date = DateFormat.format("dd MMM", dayItem.date).toString()
    HorizontalDivider()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = date,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = "${dayItem.dayTotalCalories} ккал",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
    HorizontalDivider()
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

@Preview
@Composable
private fun ItemBottomSheetContentPreview() {
    ItemBottomSheetContentView(
        item = CaloriesItem(
            date = Date(),
            caloriesFor100 = 100,
            foodName = "Борщ",
            weight = 300
        )
    )
}

@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreen(
        uiState = HistoryScreenState(
            itemBottomSheet = null,
            confirmDeleteDialogState = null,
            items = listOf(
                DayCalories(
                    dayItems = listOf(
                        CaloriesItem(
                            date = Date(),
                            caloriesFor100 = 100,
                            foodName = "Борщ",
                            weight = 300
                        )
                    ),
                    date = Date()
                )
            )
        ),
        onEvent = {},
    )
}

@Preview
@Composable
fun HistoryScreenPreviewEmpty() {
    HistoryScreen(
        uiState = HistoryScreenState(
            itemBottomSheet = null,
            confirmDeleteDialogState = null,
            items = emptyList(),
        ),
        onEvent = {},
    )
}