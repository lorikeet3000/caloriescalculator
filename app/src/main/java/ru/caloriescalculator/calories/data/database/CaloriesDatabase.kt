package ru.caloriescalculator.calories.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.caloriescalculator.calories.data.dao.CaloriesDao
import ru.caloriescalculator.calories.data.dao.ProductsDao
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import ru.caloriescalculator.calories.data.model.ProductEntity

@Database(
    entities = [
        CaloriesEntity::class,
        ProductEntity::class
    ], version = 2
)
abstract class CaloriesDatabase : RoomDatabase() {

    abstract fun caloriesDao(): CaloriesDao

    abstract fun productsDao(): ProductsDao
}