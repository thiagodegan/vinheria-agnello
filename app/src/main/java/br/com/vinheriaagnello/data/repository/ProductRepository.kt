package br.com.vinheriaagnello.data.repository

import br.com.vinheriaagnello.data.local.ProductDao
import br.com.vinheriaagnello.data.local.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    suspend fun insert(product: ProductEntity) {
        productDao.insert(product)
    }

    suspend fun update(product: ProductEntity) {
        productDao.update(product)
    }

    suspend fun delete(product: ProductEntity) {
        productDao.delete(product)
    }

    fun getAll(): Flow<List<ProductEntity>> {
        return productDao.getAll()
    }
}
