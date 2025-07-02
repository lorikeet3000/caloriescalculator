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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.caloriescalculator.calories.presentation.viewmodel.AddCaloriesViewModel

@Composable
fun AddCaloriesScreen(navController: NavController, viewModel: AddCaloriesViewModel = hiltViewModel()) {
    Column {
        Spacer(modifier = Modifier.height(32.dp))
        EnterFoodNameView(viewModel)
        Text(
            text = "and",
            fontSize = 36.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth().padding(64.dp),
            textAlign = TextAlign.Center
        )
        EnterCaloriesView(viewModel)
        Text(
            text = "or",
            fontSize = 36.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center
        )
        EnterCaloriesAndWeight(viewModel)
    }
}

@Composable
private fun EnterFoodNameView(viewModel: AddCaloriesViewModel) {
    OutlinedTextField(
        value = viewModel.foodNameValue,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = {
            viewModel.onFoodNameValueChanged(it)
        },
        label = { Text(text = "Enter food name") },
        isError = false,
    )
}

@Composable
private fun EnterCaloriesView(viewModel: AddCaloriesViewModel) {
    OutlinedTextField(
        value = viewModel.caloriesValue,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = {
            viewModel.onCalorieValueUpdated(it)
        },
        label = { Text(text = "Enter calories") },
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onClick = {
            viewModel.onCaloriesSubmitClicked()
        }
    ) {
        Text(text = "Submit")
    }
}

@Composable
private fun EnterCaloriesAndWeight(viewModel: AddCaloriesViewModel) {
    OutlinedTextField(
        value = viewModel.caloriesFor100,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = {
            viewModel.onCaloriesFor100Updated(it)
        },
        label = { Text(text = "Enter calories for 100 g/ml") },
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    OutlinedTextField(
        value = viewModel.weightValue,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onValueChange = {
            viewModel.onWeightUpdated(it)
        },
        label = { Text(text = "Enter weight/volume in g/ml") },
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onClick = {
            viewModel.onCaloriesFor100SubmitClicked()
        }
    ) {
        Text(text = "Submit")
    }
}