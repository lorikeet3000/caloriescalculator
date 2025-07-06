package ru.caloriescalculator.calories.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calories")
data class CaloriesEntity(
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "food_name") val foodName: String,
    @ColumnInfo(name = "calories") val calories: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}