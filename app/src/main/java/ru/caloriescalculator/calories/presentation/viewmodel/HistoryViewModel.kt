package ru.caloriescalculator.calories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.caloriescalculator.calories.core.DateConverter
import ru.caloriescalculator.calories.data.repository.CaloriesRepository
import ru.caloriescalculator.calories.presentation.event.HistoryEvent
import ru.caloriescalculator.calories.presentation.mapper.CaloriesMapper
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import ru.caloriescalculator.calories.presentation.model.ConfirmDeleteDialogState
import ru.caloriescalculator.calories.presentation.model.DayCalories
import ru.caloriescalculator.calories.presentation.model.HistoryScreenState
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: CaloriesRepository,
    private val mapper: CaloriesMapper,
    private val dateConverter: DateConverter,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryScreenState())
    val uiState: StateFlow<HistoryScreenState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            repository.getAllItems().collect { items ->
                val groupedByDate = items.groupBy {
                    it.date
                }
                val dayCaloriesList = mutableListOf<DayCalories>()
                groupedByDate.entries.reversed().forEach { entry ->
                    dayCaloriesList.add(
                        DayCalories(
                            dayItems = mapper.mapEntitiesToItems(entry.value),
                            date = dateConverter.convertToDate(entry.key),
                        )
                    )
                }
                _uiState.value = _uiState.value.copy(
                    items = dayCaloriesList
                )
            }
        }
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.OnItemClick -> onItemClick(event.item)
            HistoryEvent.OnItemBottomSheetClose -> onItemBottomSheetClose()
            is HistoryEvent.OnItemDeleteClick -> onItemDelete(event.item)
            is HistoryEvent.OnItemEditClick -> onItemEdit(event.item)
            is HistoryEvent.OnConfirmDeleteClick -> onConfirmItemDelete(event.item)
            HistoryEvent.OnDeleteDialogDismiss -> onDeleteDialogDismiss()
            is HistoryEvent.OnAddForTodayClick -> onAddForToday(event.item)
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