package ru.caloriescalculator.calories.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.caloriescalculator.calories.data.dao.CaloriesDao
import ru.caloriescalculator.calories.data.model.CaloriesEntity

@Database(entities = [CaloriesEntity::class], version = 1)
abstract class CaloriesDatabase : RoomDatabase() {
    abstract fun caloriesDao(): CaloriesDao
}