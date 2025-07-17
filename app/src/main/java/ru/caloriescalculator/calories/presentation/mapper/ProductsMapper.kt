package ru.caloriescalculator.calories.presentation.mapper

import ru.caloriescalculator.calories.data.model.ProductEntity
import ru.caloriescalculator.calories.presentation.model.Product
import javax.inject.Inject

class ProductsMapper
@Inject constructor() {

    fun mapEntitiesToProducts(entities: List<ProductEntity>): List<Product> {
        return entities.map { entity ->
            Product(
                caloriesFor100 = entity.caloriesFor100,
                name = entity.name,
                weight = entity.weight,
                comment = entity.comment,
                id = entity.id
            )
        }
    }

    fun mapProductToEntity(product: Product): ProductEntity {
        return ProductEntity(
            name = product.name,
            weight = product.weight,
            caloriesFor100 = product.caloriesFor100,
            comment = product.comment,
        )
    }
}