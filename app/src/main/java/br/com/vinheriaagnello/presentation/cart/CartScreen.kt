package br.com.vinheriaagnello.presentation.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.vinheriaagnello.data.local.CartItemEntity
import br.com.vinheriaagnello.data.local.ProductEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    productViewModel: br.com.vinheriaagnello.presentation.product.ProductViewModel,
    onOrderCompleted: () -> Unit,
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total by cartViewModel.total.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cart") })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total: \$${String.format("%.2f", total)}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        // Atualiza estoque
                        cartItems.forEach { item ->
                            val updatedProduct = ProductEntity(
                                id = item.productId,
                                name = item.name,
                                description = item.description,
                                price = item.price,
                                quantity = item.stock - item.quantityInCart
                            )
                            productViewModel.update(updatedProduct)
                        }

                        // Limpa carrinho
                        cartViewModel.clearCart()

                        // Mostra mensagem
                        scope.launch {
                            snackbarHostState.showSnackbar("Order completed successfully!")
                            // Volta para Home
                            onOrderCompleted()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalize Order")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(cartItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Price: \$${item.price}")
                        Text(text = "In stock: ${item.stock}")
                        Text(text = "In cart: ${item.quantityInCart}")

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            OutlinedTextField(
                                value = item.quantityInCart.toString(),
                                onValueChange = { value ->
                                    val newQty = value.toIntOrNull() ?: item.quantityInCart
                                    if (newQty in 1..item.stock) {
                                        val updatedItem = item.copy(quantityInCart = newQty)
                                        cartViewModel.update(updatedItem)
                                    }
                                },
                                label = { Text("Quantity") },
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(onClick = { cartViewModel.delete(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove")
                            }
                        }
                    }
                }
            }
        }
    }
}
