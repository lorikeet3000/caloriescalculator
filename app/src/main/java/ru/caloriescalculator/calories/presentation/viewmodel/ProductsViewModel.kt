package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.data.repository.ProductsRepository
import ru.caloriescalculator.calories.presentation.event.ProductsEvent
import ru.caloriescalculator.calories.presentation.mapper.ProductsMapper
import ru.caloriescalculator.calories.presentation.model.Product
import ru.caloriescalculator.calories.presentation.model.ProductsScreenState
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: ProductsMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsScreenState())
    val uiState: StateFlow<ProductsScreenState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.OnProductClick -> onProductClick(event.product)
        }
    }

    private fun onProductClick(product: Product) {

    }

    private fun loadProducts() {
        viewModelScope.launch(IO) {
            repository.getAllProducts().collect { items ->
                _uiState.value = _uiState.value.copy(
                    products = mapper.mapEntitiesToProducts(items)
                )
            }
        }
    }
}