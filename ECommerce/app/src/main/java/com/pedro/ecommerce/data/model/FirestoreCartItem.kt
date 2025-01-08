package com.pedro.ecommerce.data.model

data class FirestoreCartItem(
    val productId: String = "",
    var quantity: Int = 0,
    var totalPrice: Double = 0.0
)