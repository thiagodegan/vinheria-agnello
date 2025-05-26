package br.com.vinheriaagnello.data.repository

import br.com.vinheriaagnello.data.local.CartItemDao
import br.com.vinheriaagnello.data.local.CartItemEntity

class CartRepository(private val cartItemDao: CartItemDao) {

    suspend fun insert(cartItem: CartItemEntity) {
        cartItemDao.insert(cartItem)
    }

    suspend fun update(cartItem: CartItemEntity) {
        cartItemDao.update(cartItem)
    }

    suspend fun delete(cartItem: CartItemEntity) {
        cartItemDao.delete(cartItem)
    }

    suspend fun getAll(): List<CartItemEntity> {
        return cartItemDao.getAll()
    }

    suspend fun clearCart() {
        cartItemDao.clearCart()
    }
}
