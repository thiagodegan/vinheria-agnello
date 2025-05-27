package br.com.vinheriaagnello.presentation.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.vinheriaagnello.data.local.ProductEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    products: List<ProductEntity>,
    onProductClick: (ProductEntity) -> Unit,
    onAddToCartClick: (ProductEntity) -> Unit,
    onGoToCartClick: () -> Unit,
    onGoToAdminClick: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wine Store") },
                actions = {
                    IconButton(onClick = onGoToAdminClick) {
                        Icon(Icons.Filled.Settings, contentDescription = "Admin")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onGoToCartClick) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No products available. Add one!")
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = { onProductClick(product) }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = "Price: \$${product.price}")
                            Text(text = "In stock: ${product.quantity}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { onAddToCartClick(product) },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Add to Cart")
                            }
                        }
                    }
                }
            }
        }
    }
}
