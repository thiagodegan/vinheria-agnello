package br.com.vinheriaagnello.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vinheriaagnello.data.local.ProductEntity
import br.com.vinheriaagnello.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val products: StateFlow<List<ProductEntity>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedProduct = MutableStateFlow<ProductEntity?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    fun insert(product: ProductEntity) = viewModelScope.launch {
        repository.insert(product)
    }

    fun update(product: ProductEntity) = viewModelScope.launch {
        repository.update(product)
    }

    fun delete(product: ProductEntity) = viewModelScope.launch {
        repository.delete(product)
    }

    fun selectProduct(product: ProductEntity) {
        _selectedProduct.value = product
    }

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}
