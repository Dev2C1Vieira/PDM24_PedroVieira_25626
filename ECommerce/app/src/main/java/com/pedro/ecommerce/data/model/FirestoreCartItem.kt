package com.pedro.ecommerce.data.model

data class FirestoreCartItem(
    val productId: String,
    val quantity: Int,
    val totalPrice: Double
)