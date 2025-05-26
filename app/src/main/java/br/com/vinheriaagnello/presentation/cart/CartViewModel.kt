package br.com.vinheriaagnello.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vinheriaagnello.data.local.CartItemEntity
import br.com.vinheriaagnello.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemEntity>>(emptyList())
    val cartItems: StateFlow<List<CartItemEntity>> = _cartItems

    val total: StateFlow<Double> = MutableStateFlow(0.0)

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = repository.getAll()
            updateTotal()
        }
    }

    private fun updateTotal() {
        val sum = _cartItems.value.sumOf { it.price * it.quantityInCart }
        (total as MutableStateFlow).value = sum
    }

    fun insert(cartItem: CartItemEntity) {
        viewModelScope.launch {
            repository.insert(cartItem)
            loadCartItems()
        }
    }

    fun update(cartItem: CartItemEntity) {
        viewModelScope.launch {
            repository.update(cartItem)
            loadCartItems()
        }
    }

    fun delete(cartItem: CartItemEntity) {
        viewModelScope.launch {
            repository.delete(cartItem)
            loadCartItems()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
            loadCartItems()
        }
    }
}
