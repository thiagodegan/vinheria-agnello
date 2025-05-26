package br.com.vinheriaagnello.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val quantityInCart: Int
)
