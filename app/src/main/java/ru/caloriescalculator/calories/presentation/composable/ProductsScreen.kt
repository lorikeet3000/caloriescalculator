package ru.caloriescalculator.calories.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.event.ProductsEvent
import ru.caloriescalculator.calories.presentation.model.Product
import ru.caloriescalculator.calories.presentation.model.ProductsScreenState

@Composable
fun ProductsScreen(
    uiState: ProductsScreenState,
    onEvent: (ProductsEvent) -> Unit,
) {
    ProductsScreen(
        products = uiState.products,
        onProductClick = {
            onEvent(ProductsEvent.OnProductClick(it))
        },
        onAddProductClick = {
            onEvent(ProductsEvent.OnAddProductClick)
        }
    )
}

@Composable
fun ProductsScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddProductClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (products.isEmpty()) {
            Text(
                modifier = Modifier.padding(32.dp),
                text = "Пока не добавлено ни одного продукта",
                fontSize = 22.sp,
            )
        }
        AddButtonView(
            onAddProductClick = onAddProductClick
        )
        ProductsListView(
            products = products,
            onProductClick = onProductClick
        )
    }
}

@Composable
private fun ProductsListView(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
) {
    LazyColumn {
        items(products) { item ->
            ProductItemView(
                product = item,
                onClick = onProductClick
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun AddButtonView(
    onAddProductClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onAddProductClick,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Add, "Добавить продукт")
                Spacer(modifier = Modifier.size(8.dp))
                Text("Добавить продукт")
            }
        }
    }
}

@Composable
private fun ProductItemView(
    product: Product,
    onClick: (Product) -> Unit,
) {
    Column(
        modifier = Modifier.clickable {
            onClick(product)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = product.name,
                fontSize = 16.sp,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "${product.totalCalories}",
                fontSize = 22.sp,
            )
            Text(text = " ккал/100г")
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreviewEmpty() {
    ProductsScreen(
        uiState = ProductsScreenState(
            products = emptyList()
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    ProductsScreen(
        uiState = ProductsScreenState(
            products = listOf(
                Product(
                    name = "Баклажан",
                    caloriesFor100 = 65,
                    weight = 200
                )
            )
        ),
        onEvent = {}
    )
}