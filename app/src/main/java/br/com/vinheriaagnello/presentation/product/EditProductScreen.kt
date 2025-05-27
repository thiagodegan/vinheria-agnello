package br.com.vinheriaagnello.presentation.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.vinheriaagnello.data.local.ProductEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    product: ProductEntity,
    onUpdateProduct: (ProductEntity) -> Unit,
    onBack: () -> Unit
) {
    var price by remember { mutableStateOf(product.price.toString()) }
    var quantity by remember { mutableStateOf(product.quantity.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Product") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Name: ${product.name}")
            Text(text = "Description: ${product.description}")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { input ->
                    val cleanInput = input.filter { it.isDigit() || it == '.' || it == ',' }
                    price = cleanInput
                },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = quantity,
                onValueChange = { input ->
                    val cleanInput = input.filter { it.isDigit() }
                    quantity = cleanInput
                },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedProduct = product.copy(
                        price = price.replace(',', '.').toDoubleOrNull() ?: product.price,
                        quantity = quantity.toIntOrNull() ?: product.quantity
                    )
                    onUpdateProduct(updatedProduct)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Product")
            }
        }
    }
}
