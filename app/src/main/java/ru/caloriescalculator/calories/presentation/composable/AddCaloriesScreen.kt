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
import ru.caloriescalculator.calories.presentation.event.AddCaloriesEvent

@Composable
fun AddCaloriesScreen(
    foodName: String,
    calories: String,
    caloriesFor100: String,
    foodWeight: String,
    isFoodNameError: Boolean,
    onEvent: (AddCaloriesEvent) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(32.dp))
        EnterFoodNameView(
            foodName = foodName,
            isError = isFoodNameError,
            onFoodNameChanged = {
                onEvent(AddCaloriesEvent.FoodNameChange(it))
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        EnterCaloriesView(
            calories = calories,
            onCaloriesChanged = {
                onEvent(AddCaloriesEvent.CaloriesValueUpdate(it))
            },
            onCaloriesSubmitClick = {
                onEvent(AddCaloriesEvent.CaloriesSubmit)
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        EnterCaloriesAndWeight(
            caloriesFor100 = caloriesFor100,
            foodWeight = foodWeight,
            onCaloriesFor100Changed = {
                AddCaloriesEvent.CaloriesFor100Update(it)
            },
            onFoodWeightChanged = {
                AddCaloriesEvent.FoodWeightUpdate(it)
            },
            onCaloriesFor100SubmitClick = {
                AddCaloriesEvent.CaloriesFor100Submit
            }
        )
    }
}

@Composable
private fun EnterFoodNameView(
    foodName: String,
    isError: Boolean,
    onFoodNameChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = foodName,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = onFoodNameChanged,
        label = { Text(text = "Введите название продукта") },
        isError = isError,
        supportingText = {
            if (isError) {
                Text("Введите название")
            }
        }
    )
}

@Composable
private fun EnterCaloriesView(
    calories: String,
    onCaloriesChanged: (String) -> Unit,
    onCaloriesSubmitClick: () -> Unit
) {
    OutlinedTextField(
        value = calories,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = onCaloriesChanged,
        label = { Text(text = "Введите калории (ккал)") },
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onClick = onCaloriesSubmitClick
    ) {
        Text(text = "Добавить")
    }
}

@Composable
private fun EnterCaloriesAndWeight(
    caloriesFor100: String,
    foodWeight: String,
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
        isError = false,
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
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
fun AddCaloriesScreenPreview() {
    AddCaloriesScreen(
        foodName = "",
        calories = "",
        caloriesFor100 = "",
        foodWeight = "",
        isFoodNameError = true,
        onEvent = {}
    )
}