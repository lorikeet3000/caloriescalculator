package ru.caloriescalculator.calories.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @ColumnInfo(name = "product_name") val name: String,
    @ColumnInfo(name = "calories_for_100") val caloriesFor100: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "popularity") val popularity: Long = 0,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}