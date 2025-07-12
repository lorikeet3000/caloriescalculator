package ru.caloriescalculator.calories.presentation.mapper

import ru.caloriescalculator.calories.core.DateConverter
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import ru.caloriescalculator.calories.presentation.model.CaloriesItem
import javax.inject.Inject

class CaloriesMapper @Inject constructor(
    private val dateConverter: DateConverter
) {

    fun mapEntitiesToItems(entities: List<CaloriesEntity>): List<CaloriesItem> {
        return entities.map { entity ->
            val date = dateConverter.convertToDate(entity.date)
            CaloriesItem(
                date = date,
                caloriesFor100 = entity.caloriesFor100,
                foodName = entity.foodName,
                weight = entity.weight
            )
        }
    }

    fun mapItemToEntity(item: CaloriesItem): CaloriesEntity {
        val dateString = dateConverter.convertToString(item.date)
        return CaloriesEntity(
            date = dateString,
            foodName = item.foodName,
            weight = item.weight,
            caloriesFor100 = item.caloriesFor100
        )
    }
}