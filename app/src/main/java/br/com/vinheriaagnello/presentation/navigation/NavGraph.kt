package br.com.vinheriaagnello.presentation.navigation

import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
import br.com.vinheriaagnello.data.local.WineStoreDatabase
import br.com.vinheriaagnello.data.repository.ProductRepository
import br.com.vinheriaagnello.presentation.cart.CartViewModel
import br.com.vinheriaagnello.presentation.cart.CartScreen
import br.com.vinheriaagnello.presentation.cart.CartViewModelFactory
import br.com.vinheriaagnello.data.repository.CartRepository
import br.com.vinheriaagnello.presentation.product.ProductDetailScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val context = LocalContext.current

            val database = remember {
                WineStoreDatabase.getDatabase(context)
            }
            val repository = remember { ProductRepository(database.productDao()) }

            val viewModel: ProductViewModel = viewModel(
                factory = ProductViewModelFactory(repository)
            )

            val cartRepository = remember { CartRepository(database.cartItemDao()) }
            val cartViewModel: CartViewModel = viewModel(
                factory = CartViewModelFactory(cartRepository)
            )

            val products by viewModel.products.collectAsState()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            HomeScreen(
                products = products,
                onProductClick = { product ->
                    navController.navigate("product/${product.id}")
                },
                onAddToCartClick = { product ->
                    val cartItem = br.com.vinheriaagnello.data.local.CartItemEntity(
                        productId = product.id,
                        name = product.name,
                        description = product.description,
                        price = product.price,
                        stock = product.quantity,
                        quantityInCart = 1
                    )
                    cartViewModel.insert(cartItem)
                    scope.launch {
                        snackbarHostState.showSnackbar("Added to cart!")
                    }
                },
                onGoToCartClick = {
                    navController.navigate("cart")
                },
                onGoToAdminClick = {
                    navController.navigate("admin/add-product")
                },
                snackbarHostState = snackbarHostState
            )
        }
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            val context = LocalContext.current
            val database = remember { WineStoreDatabase.getDatabase(context) }
            val productRepository = remember { ProductRepository(database.productDao()) }
            val cartRepository = remember { CartRepository(database.cartItemDao()) }

            val productViewModel: ProductViewModel = viewModel(
                factory = ProductViewModelFactory(productRepository)
            )
            val cartViewModel: CartViewModel = viewModel(
                factory = CartViewModelFactory(cartRepository)
            )

            val products by productViewModel.products.collectAsState()
            val product = products.find { it.id == productId }

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            if (product != null) {

                ProductDetailScreen(
                    product = product,
                    onAddToCartClick = { productToAdd ->
                        val cartItem = br.com.vinheriaagnello.data.local.CartItemEntity(
                            productId = productToAdd.id,
                            name = productToAdd.name,
                            description = productToAdd.description,
                            price = productToAdd.price,
                            stock = productToAdd.quantity,
                            quantityInCart = 1
                        )
                        cartViewModel.insert(cartItem)
                        scope.launch {
                            snackbarHostState.showSnackbar("Added to cart!")
                        }
                    },
                    onBackClick = { navController.popBackStack() },
                    snackbarHostState = snackbarHostState
                )
            }
        }
        composable("cart") {
            val context = LocalContext.current
            val database = remember { WineStoreDatabase.getDatabase(context) }
            val cartRepository = remember { CartRepository(database.cartItemDao()) }
            val productRepository = remember { ProductRepository(database.productDao()) }

            val cartViewModel: CartViewModel = viewModel(
                factory = CartViewModelFactory(cartRepository)
            )
            val productViewModel: ProductViewModel = viewModel(
                factory = ProductViewModelFactory(productRepository)
            )

            CartScreen(
                cartViewModel = cartViewModel,
                productViewModel = productViewModel,
                onOrderCompleted = {
                    navController.popBackStack()
                }
            )
        }
        composable("checkout") {
            // TODO: Replace with CheckoutScreen()
        }
        composable("admin/add-product") {
            val context = LocalContext.current
            val database = remember { WineStoreDatabase.getDatabase(context) }
            val repository = remember { ProductRepository(database.productDao()) }
            val viewModel: ProductViewModel = viewModel(
                factory = ProductViewModelFactory(repository)
            )

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
