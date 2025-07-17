package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnterFoodNameView(
    foodName: String,
    isFoodNameError: Boolean,
    onFoodNameChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = foodName,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = onFoodNameChanged,
        isError = isFoodNameError,
        supportingText = {
            if (isFoodNameError) {
                Text("Название продукта не должно быть пустым")
            }
        },
        label = { Text(text = "Введите название продукта") },
    )
}