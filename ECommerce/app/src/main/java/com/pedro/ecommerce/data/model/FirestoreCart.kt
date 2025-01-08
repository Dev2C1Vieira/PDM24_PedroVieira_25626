package com.pedro.ecommerce.data.model

data class FirestoreCart(
    val id: String = "",
    val userId: String = "",
    val items: List<FirestoreCartItem> = emptyList(),
    val totalPrice: Double = 0.0
)