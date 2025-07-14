package ru.caloriescalculator.calories.presentation.composable

import android.text.format.DateFormat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.event.HistoryEvent
import ru.caloriescalculator.calories.presentation.event.HistoryEvent.OnDeleteDialogDismiss
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.DayCalories
import ru.caloriescalculator.calories.presentation.model.HistoryConfirmDeleteDialogState
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    allItems: List<DayCalories>,
    itemBottomSheet: CaloriesItem? = null,
    confirmDeleteDialogState: HistoryConfirmDeleteDialogState?,
    onEvent: (HistoryEvent) -> Unit
) {
    if (itemBottomSheet != null) {
        ItemBottomSheetView(
            item = itemBottomSheet,
            onBottomSheetDismiss = {
                onEvent(HistoryEvent.OnItemBottomSheetClose)
            },
            onEditClick = {
                onEvent(HistoryEvent.OnItemEditClick(it))
            },
            onDeleteClick = {
                onEvent(HistoryEvent.OnItemDeleteClick(it))
            }
        )
    }
    if (confirmDeleteDialogState != null) {
        ConfirmDeleteDialog(
            state = confirmDeleteDialogState,
            onDismissClick = {
                onEvent(OnDeleteDialogDismiss)
            },
            onConfirmDeleteClick = {
                onEvent(HistoryEvent.OnConfirmDeleteClick(it))
            }
        )
    }
    LazyColumn {
        allItems.forEach { dayItem ->
            stickyHeader {
                DayItem(dayItem)
            }
            items(dayItem.dayItems) { caloriesItem ->
                CaloriesItemView(
                    item = caloriesItem,
                    onClick = {
                        onEvent(HistoryEvent.OnItemClick(it))
                    }
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
    onEditClick: (CaloriesItem) -> Unit,
    onDeleteClick: (CaloriesItem) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onBottomSheetDismiss
    ) {
        ItemBottomSheetContent(
            item = item,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
private fun ItemBottomSheetContent(
    item: CaloriesItem,
    onEditClick: (CaloriesItem) -> Unit = {},
    onDeleteClick: (CaloriesItem) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            text = "${item.foodName}(${item.totalCalories} ккал)",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                onEditClick(item)
            }
                .padding(16.dp),
            text = "Редактировать"
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onDeleteClick(item)
                }
                .padding(16.dp),
            text = "Удалить",
            color = Color.Red
        )
    }
}

@Composable
private fun ConfirmDeleteDialog(
    state: HistoryConfirmDeleteDialogState,
    onDismissClick: () -> Unit,
    onConfirmDeleteClick: (CaloriesItem) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmDeleteClick(state.item)
                }
            ) {
                Text("Удалить")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissClick()
                }
            ) {
                Text("Отмена")
            }
        },
        text = {
            Text(text = "Вы уверены, что хотите удалить запись?")
        }
    )
}

@Preview
@Composable
private fun ItemBottomSheetContentPreview() {
    ItemBottomSheetContent(
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
        onEvent = {},
        itemBottomSheet = null,
        confirmDeleteDialogState = null,
        allItems = listOf(
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
    )
}