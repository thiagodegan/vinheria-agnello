package br.com.vinheriaagnello.data.local

import androidx.room.*

@Dao
interface CartItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItemEntity)

    @Update
    suspend fun update(cartItem: CartItemEntity)

    @Delete
    suspend fun delete(cartItem: CartItemEntity)

    @Query("SELECT * FROM cart_items ORDER BY name ASC")
    suspend fun getAll(): List<CartItemEntity>

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
