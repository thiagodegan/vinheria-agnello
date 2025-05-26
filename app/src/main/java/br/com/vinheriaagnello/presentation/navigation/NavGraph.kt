package br.com.vinheriaagnello.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.vinheriaagnello.presentation.product.HomeScreen
import br.com.vinheriaagnello.presentation.product.AddProductScreen
import br.com.vinheriaagnello.presentation.product.ProductViewModel
import br.com.vinheriaagnello.presentation.product.ProductViewModelFactory
import androidx.compose.runtime.collectAsState
import br.com.vinheriaagnello.data.local.WineStoreDatabase
import br.com.vinheriaagnello.data.repository.ProductRepository

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val context = LocalContext.current

            val database = remember {
                br.com.vinheriaagnello.data.local.WineStoreDatabase.getDatabase(context)
            }
            val repository = remember { br.com.vinheriaagnello.data.repository.ProductRepository(database.productDao()) }

            val viewModel: ProductViewModel = viewModel(
                factory = ProductViewModelFactory(repository)
            )

            val products by viewModel.products.collectAsState()
            HomeScreen(
                products = products, // depois integraremos com o ViewModel
                onProductClick = { /* TODO */ },
                onAddToCartClick = { /* TODO */ },
                onGoToCartClick = { /* TODO */ },
                onGoToAdminClick = {
                    navController.navigate("admin/add-product")
                }
            )
        }
        composable("product/{productId}") {
            // TODO: Replace with ProductDetailScreen(productId)
        }
        composable("cart") {
            // TODO: Replace with CartScreen()
        }
        composable("checkout") {
            // TODO: Replace with CheckoutScreen()
        }
        composable("admin/add-product") {
            val context = LocalContext.current
            val database = remember { br.com.vinheriaagnello.data.local.WineStoreDatabase.getDatabase(context) }
            val repository = remember { br.com.vinheriaagnello.data.repository.ProductRepository(database.productDao()) }
            val viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(repository))

            AddProductScreen(
                onProductAdded = {
                    // Voltar para a Home após adicionar:
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
    }
}
