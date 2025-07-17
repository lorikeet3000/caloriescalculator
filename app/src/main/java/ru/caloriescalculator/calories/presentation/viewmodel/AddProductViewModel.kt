package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.data.repository.ProductsRepository
import ru.caloriescalculator.calories.presentation.effect.ProductEffect
import ru.caloriescalculator.calories.presentation.event.AddProductEvent
import ru.caloriescalculator.calories.presentation.mapper.ProductsMapper
import ru.caloriescalculator.calories.presentation.model.AddProductScreenState
import ru.caloriescalculator.calories.presentation.model.Product
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val mapper: ProductsMapper,
    private val repository: ProductsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductScreenState())
    val uiState: StateFlow<AddProductScreenState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<ProductEffect>()
    val effectsSharedFlow = _effects.asSharedFlow()

    var foodNameValue by mutableStateOf("")
        private set

    var caloriesFor100Value by mutableStateOf("")
        private set

    var foodWeightValue by mutableStateOf("")
        private set

    var commentValue by mutableStateOf("")
        private set

    fun onEvent(event: AddProductEvent) {
        when (event) {
            AddProductEvent.ProductDataSubmit -> onDataSubmit()
            is AddProductEvent.CaloriesFor100Update -> onCaloriesFor100Update(event.newValue)
            is AddProductEvent.FoodNameChange -> onFoodNameChange(event.newValue)
            is AddProductEvent.FoodWeightUpdate -> onFoodWeightUpdate(event.newValue)
            is AddProductEvent.CommentUpdate -> onCommentUpdate(event.newWalue)
        }
    }

    private fun onCommentUpdate(value: String) {
        commentValue = value
    }

    private fun onFoodWeightUpdate(value: String) {
        foodWeightValue = value
    }

    private fun onFoodNameChange(value: String) {
        foodNameValue = value
        _uiState.value = _uiState.value.copy(
            isFoodNameError = false
        )
    }

    private fun onCaloriesFor100Update(value: String) {
        caloriesFor100Value = value
        _uiState.value = _uiState.value.copy(
            isCaloriesError = false
        )
    }

    private fun onDataSubmit() {
        val caloriesFor100 = caloriesFor100Value.toIntOrNull()
        val weight = foodWeightValue.toIntOrNull()
        if (foodNameValue.isEmpty()) {
            _uiState.value = _uiState.value.copy(isFoodNameError = true)
            return
        }
        if (caloriesFor100 == null) {
            _uiState.value = _uiState.value.copy(isCaloriesError = true)
            return
        }
        saveProduct(
            foodName = foodNameValue,
            caloriesFor100 = caloriesFor100,
            weight = weight,
            comment = commentValue,
        )
    }

    private fun saveProduct(foodName: String, caloriesFor100: Int, weight: Int?, comment: String) {
        viewModelScope.launch(IO) {
            val item = Product(
                caloriesFor100 = caloriesFor100,
                name = foodName,
                weight = weight ?: 0,
                comment = comment
            )
            val entity = mapper.mapProductToEntity(item)
            repository.addProduct(entity)
            _effects.emit(ProductEffect.CloseScreen)
        }
    }
}