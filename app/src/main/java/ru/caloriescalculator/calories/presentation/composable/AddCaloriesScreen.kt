package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.caloriescalculator.calories.presentation.event.AddCaloriesEvent
import ru.caloriescalculator.calories.presentation.model.AddCaloriesConfirmDialogState
import ru.caloriescalculator.calories.presentation.model.AddCaloriesScreenState

@Composable
fun AddCaloriesScreen(
    foodName: String,
    caloriesFor100: String,
    foodWeight: String,
    uiState: AddCaloriesScreenState,
    onScreenClose: () -> Unit,
    onEvent: (AddCaloriesEvent) -> Unit
) {
    if (uiState.confirmDialogState != null) {
        ConfirmDialog(
            state = uiState.confirmDialogState,
            onDismissClick = onScreenClose
        )
    }

    Column {
        Spacer(modifier = Modifier.height(32.dp))
        EnterFoodNameView(
            foodName = foodName,
            isFoodNameError = uiState.isFoodNameError,
            onFoodNameChanged = {
                onEvent(AddCaloriesEvent.FoodNameChange(it))
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        EnterCaloriesAndWeight(
            caloriesFor100 = caloriesFor100,
            foodWeight = foodWeight,
            isCaloriesError = uiState.isCaloriesError,
            isWeightError = uiState.isWeightError,
            evaluatedCalories = uiState.evaluatedCalories,
            onCaloriesFor100Changed = {
                onEvent(AddCaloriesEvent.CaloriesFor100Update(it))
            },
            onFoodWeightChanged = {
                onEvent(AddCaloriesEvent.FoodWeightUpdate(it))
            },
            onCaloriesFor100SubmitClick = {
                onEvent(AddCaloriesEvent.CaloriesFor100Submit)
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
private fun EnterFoodNameView(
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

@Composable
private fun EnterCaloriesAndWeight(
    caloriesFor100: String,
    foodWeight: String,
    isCaloriesError: Boolean,
    isWeightError: Boolean,
    evaluatedCalories: Int,
    onCaloriesFor100Changed: (String) -> Unit,
    onFoodWeightChanged: (String) -> Unit,
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
        supportingText = {
            if (isCaloriesError) {
                Text("Введите целое число")
            }
        },
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
        isError = isWeightError,
        supportingText = {
            if (isWeightError) {
                Text("Введите целое число")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 32.dp),
        textAlign = TextAlign.Center,
        text = "Вы добавите $evaluatedCalories ккал"
    )
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
private fun ConfirmDialog(
    state: AddCaloriesConfirmDialogState,
    onDismissClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            TextButton(
                onClick = onDismissClick
            ) {
                Text("Хорошо")
            }
        },
        text = {
            val annotatedString = buildAnnotatedString {
                append("Добавили ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(state.foodName)
                }
                append(" с калорийностью ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${state.calories} ккал")
                }
            }
            Text(text = annotatedString)
        }
    )
}

@Composable
@Preview
fun AddCaloriesScreenPreview() {
    AddCaloriesScreen(
        foodName = "",
        caloriesFor100 = "",
        foodWeight = "",
        uiState = AddCaloriesScreenState(
            isFoodNameError = false,
            isCaloriesError = false,
            isWeightError = false,
            confirmDialogState = null,
            evaluatedCalories = 100,
        ),
        onScreenClose = {},
        onEvent = {}
    )
}