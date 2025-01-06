package com.pedro.ecommerce.data.model

data class Product(
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0,
    val category: String = ""
)