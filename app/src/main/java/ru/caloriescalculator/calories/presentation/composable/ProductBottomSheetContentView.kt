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
import ru.caloriescalculator.calories.presentation.model.Product

@Composable
fun ProductBottomSheetContentView(
    product: Product,
    onAddForTodayClick: (Product) -> Unit = {},
    onDeleteClick: (Product) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            text = "${product.name}(${product.totalCalories} ккал/${product.weight}г)",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${product.caloriesFor100} ккал/100г",
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onAddForTodayClick(product)
                }
                .padding(16.dp),
            text = "Добавить к сегодняшнему дню"
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onDeleteClick(product)
                }
                .padding(16.dp),
            text = "Удалить",
            color = Color.Red
        )
    }
}