package ru.caloriescalculator.calories.data.repository

import kotlinx.coroutines.flow.Flow
import ru.caloriescalculator.calories.data.dao.ProductsDao
import ru.caloriescalculator.calories.data.model.ProductEntity
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    val productsDao: ProductsDao
) {

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productsDao.getAll()
    }

    suspend fun addProduct(productEntity: ProductEntity) {
        productsDao.insertProduct(productEntity)
    }

    suspend fun deleteProduct(id: Long) {
        productsDao.deleteProduct(id)
    }
}