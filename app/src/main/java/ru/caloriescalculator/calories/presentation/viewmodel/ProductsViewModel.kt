package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.data.repository.ProductsRepository
import ru.caloriescalculator.calories.presentation.event.ProductsEvent
import ru.caloriescalculator.calories.presentation.mapper.CaloriesMapper
import ru.caloriescalculator.calories.presentation.mapper.ProductsMapper
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.ConfirmDeleteDialogState
import ru.caloriescalculator.calories.presentation.model.Product
import ru.caloriescalculator.calories.presentation.model.ProductsScreenState
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val caloriesRepository: CaloriesRepository,
    private val productsMapper: ProductsMapper,
    private val caloriesMapper: CaloriesMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsScreenState())
    val uiState: StateFlow<ProductsScreenState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.OnProductClick -> onProductClick(event.product)
            is ProductsEvent.OnAddForTodayClick -> onAddForToday(event.product)
            ProductsEvent.OnBottomSheetDismissClick -> onBottomSheetDismiss()
            is ProductsEvent.OnProductDeleteClick -> onProductDelete(event.product)
            is ProductsEvent.OnConfirmDeleteClick -> onConfirmProductDelete(event.id)
            ProductsEvent.OnDeleteDialogDismiss -> onDeleteDialogDismiss()
        }
    }

    private fun onProductDelete(product: Product) {
        _uiState.value = _uiState.value.copy(
            confirmDeleteDialogState = ConfirmDeleteDialogState(
                id = product.id
            )
        )
    }

    private fun onDeleteDialogDismiss() {
        _uiState.value = _uiState.value.copy(
            confirmDeleteDialogState = null
        )
    }

    private fun onConfirmProductDelete(id: Long) {
        viewModelScope.launch(IO) {
            productsRepository.deleteProduct(id)
        }
        closeBottomSheet()
        _uiState.value = _uiState.value.copy(
            confirmDeleteDialogState = null
        )
    }

    private fun closeBottomSheet() {
        _uiState.value = _uiState.value.copy(
            productBottomSheet = null
        )
    }

    private fun onBottomSheetDismiss() {
        closeBottomSheet()
    }

    private fun onAddForToday(product: Product) {
        viewModelScope.launch(IO) {
            val newItem = CaloriesItem(
                date = Date(),
                caloriesFor100 = product.caloriesFor100,
                foodName = product.name,
                weight = product.weight,
            )
            caloriesRepository.addCalories(caloriesMapper.mapItemToEntity(newItem))
            productsRepository.increasePopularity(product.id)
        }
        closeBottomSheet()
    }

    private fun onProductClick(product: Product) {
        _uiState.value = _uiState.value.copy(
            productBottomSheet = product
        )
    }

    private fun loadProducts() {
        viewModelScope.launch(IO) {
            productsRepository.getAllProducts().collect { items ->
                val sortedProducts = items.sortedByDescending {
                    it.popularity
                }
                _uiState.value = _uiState.value.copy(
                    products = productsMapper.mapEntitiesToProducts(sortedProducts)
                )
            }
        }
    }
}