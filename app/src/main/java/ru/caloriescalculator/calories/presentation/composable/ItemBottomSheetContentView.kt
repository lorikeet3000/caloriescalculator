package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.model.CaloriesItem

@Composable
fun ItemBottomSheetContentView(
    item: CaloriesItem,
    onAddForTodayClick: (CaloriesItem) -> Unit = {},
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
                    onAddForTodayClick(item)
                }
                .padding(16.dp),
            text = "Добавить к сегодняшнему дню"
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