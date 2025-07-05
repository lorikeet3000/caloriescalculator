package ru.caloriescalculator.calories.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.caloriescalculator.calories.data.dao.CaloriesDao
import ru.caloriescalculator.calories.data.database.CaloriesDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideCaloriesDatabase(@ApplicationContext context: Context): CaloriesDatabase {
        return Room.databaseBuilder(
            context,
            CaloriesDatabase::class.java, "calories.db"
        ).build()
    }

    @Provides
    fun provideCaloriesDao(caloriesDatabase: CaloriesDatabase): CaloriesDao {
        return caloriesDatabase.caloriesDao()
    }
}