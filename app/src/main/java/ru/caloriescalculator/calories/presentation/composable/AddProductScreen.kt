package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.caloriescalculator.calories.presentation.event.AddProductEvent
import ru.caloriescalculator.calories.presentation.model.AddProductScreenState

@Composable
fun AddProductScreen(
    foodName: String,
    caloriesFor100: String,
    foodWeight: String,
    comment: String,
    uiState: AddProductScreenState,
    onEvent: (AddProductEvent) -> Unit,
    onScreenClose: () -> Unit = {},
) {
    Column {
        Spacer(modifier = Modifier.height(32.dp))
        EnterFoodNameView(
            foodName = foodName,
            isFoodNameError = uiState.isFoodNameError,
            onFoodNameChanged = {
                onEvent(AddProductEvent.FoodNameChange(it))
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        EnterProductData(
            caloriesFor100 = caloriesFor100,
            foodWeight = foodWeight,
            comment = comment,
            isCaloriesError = uiState.isCaloriesError,
            onCaloriesFor100Changed = {
                onEvent(AddProductEvent.CaloriesFor100Update(it))
            },
            onFoodWeightChanged = {
                onEvent(AddProductEvent.FoodWeightUpdate(it))
            },
            onCommentChanged = {
                onEvent(AddProductEvent.CommentUpdate(it))
            },
            onCaloriesFor100SubmitClick = {
                onEvent(AddProductEvent.ProductDataSubmit)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            onClick = onScreenClose
        ) {
            Text(text = "Закрыть")
        }
    }
}

@Composable
private fun EnterProductData(
    caloriesFor100: String,
    foodWeight: String,
    comment: String,
    isCaloriesError: Boolean,
    onCaloriesFor100Changed: (String) -> Unit,
    onFoodWeightChanged: (String) -> Unit,
    onCommentChanged: (String) -> Unit,
    onCaloriesFor100SubmitClick: () -> Unit
) {
    OutlinedTextField(
        value = caloriesFor100,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = onCaloriesFor100Changed,
        label = { Text(text = "Введите калории на 100 г/мл") },
        isError = isCaloriesError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    OutlinedTextField(
        value = foodWeight,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = onFoodWeightChanged,
        label = { Text(text = "Введите вес/объем в г/мл") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    OutlinedTextField(
        value = comment,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = onCommentChanged,
        label = { Text(text = "Введите комментарий") },
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onClick = onCaloriesFor100SubmitClick
    ) {
        Text(text = "Добавить")
    }
}

@Composable
@Preview
private fun AddProductPreview() {
    AddProductScreen(
        foodName = "Баклажан",
        caloriesFor100 = "123",
        foodWeight = "",
        comment = "",
        uiState = AddProductScreenState(
            isFoodNameError = true,
            isCaloriesError = false
        ),
        onEvent = {},
    )
}