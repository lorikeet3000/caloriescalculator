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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.caloriescalculator.calories.presentation.event.ProductsEvent
import ru.caloriescalculator.calories.presentation.event.ProductsEvent.OnConfirmDeleteClick
import ru.caloriescalculator.calories.presentation.event.ProductsEvent.OnDeleteDialogDismiss
import ru.caloriescalculator.calories.presentation.model.Product
import ru.caloriescalculator.calories.presentation.model.ProductsScreenState

@Composable
fun ProductsScreen(
    uiState: ProductsScreenState,
    onAddProductClick: () -> Unit = {},
    onEvent: (ProductsEvent) -> Unit = {},
) {
    if (uiState.productBottomSheet != null) {
        ProductBottomSheetView(
            product = uiState.productBottomSheet,
            onBottomSheetDismiss = {
                onEvent(ProductsEvent.OnBottomSheetDismissClick)
            },
            onAddForTodayClick = {
                onEvent(ProductsEvent.OnAddForTodayClick(it))
            },
            onDeleteClick = {
                onEvent(ProductsEvent.OnProductDeleteClick(it))
            }
        )
    }
    if (uiState.confirmDeleteDialogState != null) {
        ConfirmDeleteDialogView(
            state = uiState.confirmDeleteDialogState,
            onDismissClick = {
                onEvent(OnDeleteDialogDismiss)
            },
            onConfirmDeleteClick = {
                onEvent(OnConfirmDeleteClick(it))
            }
        )
    }
    ProductsScreen(
        products = uiState.products,
        onProductClick = {
            onEvent(ProductsEvent.OnProductClick(it))
        },
        onAddProductClick = onAddProductClick,
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
        Spacer(modifier = Modifier.size(32.dp))
        AddProductButtonView(
            onAddProductClick = onAddProductClick
        )
        Spacer(modifier = Modifier.size(32.dp))
        HorizontalDivider()
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
private fun AddProductButtonView(
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
        val bottomPadding = if (product.comment.isNotEmpty()) {
            0.dp
        } else {
            16.dp
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp,  bottom = bottomPadding)
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
            Text(text = " ккал/${product.weight}г")
        }
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(end = 16.dp),
            text = "популярность: ${product.popularity}",
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End,
        )
        if (product.comment.isNotEmpty()) {
            Text(
                text = product.comment,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 16.dp)

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductBottomSheetView(
    product: Product,
    onBottomSheetDismiss: () -> Unit,
    onAddForTodayClick: (Product) -> Unit,
    onDeleteClick: (Product) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onBottomSheetDismiss
    ) {
        ProductBottomSheetContentView(
            product = product,
            onAddForTodayClick = onAddForTodayClick,
            onDeleteClick = onDeleteClick
        )
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
                    weight = 20,
                    comment = "1 слайс",
                    popularity = 5
                )
            )
        ),
        onEvent = {}
    )
}