package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.event.ProfileEvent
import ru.caloriescalculator.calories.presentation.model.ProfileScreenState

@Composable
fun ProfileScreen(
    uiState: ProfileScreenState,
    totalCaloriesValue: String,
    onEvent: (ProfileEvent) -> Unit,
) {
    ProfileScreen(
        totalCaloriesValue = totalCaloriesValue,
        isTotalCaloriesError = uiState.isTotalCaloriesError,
        onTotalCaloriesValueChanged = {
            onEvent(ProfileEvent.OnTotalCaloriesChanged(it))
        }
    )
}

@Composable
private fun ProfileScreen(
    totalCaloriesValue: String,
    isTotalCaloriesError: Boolean,
    onTotalCaloriesValueChanged: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = "Введите целевое количество калорий в день:",
            fontSize = 20.sp,
        )
        OutlinedTextField(
            value = totalCaloriesValue,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            onValueChange = onTotalCaloriesValueChanged,
            isError = isTotalCaloriesError,
            supportingText = {
                if (isTotalCaloriesError) {
                    Text("Количество не должно быть пустым")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(
        totalCaloriesValue = "1300",
        isTotalCaloriesError = false,
    )
}