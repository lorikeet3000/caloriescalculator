package ru.caloriescalculator.calories.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.caloriescalculator.calories.data.model.ProductEntity

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductEntity>>

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("DELETE FROM products WHERE id=:id")
    suspend fun deleteProduct(id: Long)
}