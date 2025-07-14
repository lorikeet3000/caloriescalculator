package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.model.CaloriesItem

@Composable
fun CaloriesItemView(
    item: CaloriesItem,
    onClick: (CaloriesItem) -> Unit = {}
) {
    Column(
        modifier = Modifier.clickable {
            onClick(item)
        }
    ) {
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