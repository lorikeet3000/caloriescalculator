package ru.caloriescalculator.calories.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.caloriescalculator.calories.data.dao.CaloriesDao
import ru.caloriescalculator.calories.data.datastore.dataStore
import ru.caloriescalculator.calories.data.model.CaloriesEntity
import javax.inject.Inject

private val TOTAL_CALORIES = intPreferencesKey("total_calories")

class CaloriesRepository @Inject constructor(
    private val caloriesDao: CaloriesDao,
    @ApplicationContext private val appContext: Context,
) {

    suspend fun addCalories(caloriesEntity: CaloriesEntity) {
        caloriesDao.insertAll(caloriesEntity)
    }

    fun getForDate(date: String): Flow<List<CaloriesEntity>> {
        return caloriesDao.getForDate(date)
    }

    fun getAllItems(): Flow<List<CaloriesEntity>> {
        return caloriesDao.getAll()
    }

    suspend fun deleteItem(id: Long) {
        caloriesDao.deleteItem(id)
    }

    fun getTotalCalories(): Flow<Int> {
        return appContext.dataStore.data
            .map { preferences ->
                preferences[TOTAL_CALORIES] ?: 0
            }
    }

    suspend fun saveTotalCalories(value: Int) {
        appContext.dataStore.edit { settings ->
            settings[TOTAL_CALORIES] = value
        }
    }
}