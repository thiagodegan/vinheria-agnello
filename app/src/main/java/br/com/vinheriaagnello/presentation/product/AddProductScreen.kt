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
fun AddProductScreen(
    onProductAdded: () -> Unit,
    viewModel: ProductViewModel
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { input ->
                    // Permite apenas números e ponto/vírgula
                    val cleanInput = input.filter { it.isDigit() || it == '.' || it == ',' }
                    price = cleanInput
                },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val product = ProductEntity(
                        name = name,
                        description = description,
                        price = price.replace(',', '.').toDoubleOrNull() ?: 0.0,
                        quantity = quantity.toIntOrNull() ?: 0
                    )
                    viewModel.insert(product)
                    onProductAdded()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Product")
            }
        }
    }
}
