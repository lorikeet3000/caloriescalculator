package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.core.DateConverter
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.event.HomeEvent
import ru.caloriescalculator.calories.presentation.mapper.CaloriesMapper
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.ConfirmDeleteDialogState
import ru.caloriescalculator.calories.presentation.model.HomeScreenState
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CaloriesRepository,
    private val mapper: CaloriesMapper,
    private val dateConverter: DateConverter
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        loadItems()
        loadTotalCalories()
        getTodayDate()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnAddForTodayClick -> onAddForToday(event.item)
            is HomeEvent.OnConfirmDeleteClick -> onConfirmItemDelete(event.item)
            HomeEvent.OnDeleteDialogDismiss -> onDeleteDialogDismiss()
            HomeEvent.OnItemBottomSheetClose -> onItemBottomSheetClose()
            is HomeEvent.OnItemClick -> onItemClick(event.item)
            is HomeEvent.OnItemDeleteClick -> onItemDelete(event.item)
            is HomeEvent.OnItemEditClick -> onItemEdit(event.item)
            HomeEvent.OnPullToRefresh -> onPullToRefresh()
        }
    }

    private fun onPullToRefresh() {
        _isRefreshing.update { true }
        loadItems()
        getTodayDate()
    }

    private fun getTodayDate() {
        _uiState.value = _uiState.value.copy(
            date = dateConverter.getTodayDateString()
        )
    }

    private fun loadItems() {
        viewModelScope.launch(IO) {
            val todayDateString = dateConverter.convertToString(Date())
            repository.getForDate(todayDateString)
                .collect { items ->
                    _uiState.value = _uiState.value.copy(
                        items = mapper.mapEntitiesToItems(items)
                    )
                    _isRefreshing.update { false }
                }
        }
    }

    private fun loadTotalCalories() {
        viewModelScope.launch {
            repository.getTotalCalories().collect { totalCalories ->
                _uiState.value = _uiState.value.copy(
                    todayTotalCalories = totalCalories
                )
            }
        }
    }

    private fun onAddForToday(item: CaloriesItem) {
        viewModelScope.launch(IO) {
            val newItem = item.copy(
                date = Date(),
                id = 0
            )
            repository.addCalories(mapper.mapItemToEntity(newItem))
        }
        closeBottomSheet()
    }

    private fun closeBottomSheet() {
        _uiState.value = _uiState.value.copy(
            itemBottomSheet = null
        )
    }

    private fun onConfirmItemDelete(item: CaloriesItem) {
        viewModelScope.launch(IO) {
            repository.deleteItem(item.id)
        }
        closeBottomSheet()
        _uiState.value = _uiState.value.copy(
            confirmDeleteDialogState = null
        )
    }

    private fun onItemEdit(item: CaloriesItem) {

    }

    private fun onItemDelete(item: CaloriesItem) {
        _uiState.value = _uiState.value.copy(
            confirmDeleteDialogState = ConfirmDeleteDialogState(
                item = item
            )
        )
    }

    private fun onDeleteDialogDismiss() {
        _uiState.value = _uiState.value.copy(
            confirmDeleteDialogState = null
        )
    }

    private fun onItemClick(item: CaloriesItem) {
        _uiState.value = _uiState.value.copy(
            itemBottomSheet = item
        )
    }

    private fun onItemBottomSheetClose() {
        _uiState.value = _uiState.value.copy(
            itemBottomSheet = null
        )
    }
}