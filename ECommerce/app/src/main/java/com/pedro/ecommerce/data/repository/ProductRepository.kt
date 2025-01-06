package com.pedro.ecommerce.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.pedro.ecommerce.data.model.Product
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val result = db.collection("products").get().await()
        for (document in result.documents) {
            val product = document.toObject(Product::class.java)
            if (product != null) {
                products.add(product)
            }
        }
        return products
    }
}