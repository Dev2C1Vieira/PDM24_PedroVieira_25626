package com.pedro.ecommerce.viewmodel.cart

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.pedro.ecommerce.data.model.CartItem
import com.pedro.ecommerce.data.model.FirestoreCart
import com.pedro.ecommerce.data.model.FirestoreCartItem
import com.pedro.ecommerce.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    val totalCartPrice: StateFlow<Double> = _cartItems.map { cartItems ->
        cartItems.sumOf { it.totalPrice }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    fun addToCart(product: Product) {
        val currentCart = _cartItems.value.toMutableList()
        val existingItem = currentCart.find { it.product.id == product.id }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentCart.add(CartItem(product))
        }

        _cartItems.value = currentCart
    }

    fun removeFromCart(product: Product) {
        val currentCart = _cartItems.value.toMutableList()
        currentCart.removeAll { it.product.id == product.id }
        _cartItems.value = currentCart
    }

    fun increaseQuantity(product: Product) {
        val currentCart = _cartItems.value.toMutableList()
        val itemIndex = currentCart.indexOfFirst { it.product.id == product.id }
        if (itemIndex != -1) {
            val updatedItem = currentCart[itemIndex].copy(
                quantity = currentCart[itemIndex].quantity + 1
            )
            currentCart[itemIndex] = updatedItem
            _cartItems.value = currentCart
        }
    }

    fun decreaseQuantity(product: Product) {
        val currentCart = _cartItems.value.toMutableList()
        val itemIndex = currentCart.indexOfFirst { it.product.id == product.id }
        if (itemIndex != -1) {
            val currentItem = currentCart[itemIndex]
            if (currentItem.quantity > 1) {
                val updatedItem = currentItem.copy(quantity = currentItem.quantity - 1)
                currentCart[itemIndex] = updatedItem
            } else {
                currentCart.removeAt(itemIndex)
            }
            _cartItems.value = currentCart
        }
    }

    /**
     * Função para carregar o carrinho do utilizador logado do Firestore.
     */
    fun loadCart(userId: String) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("carts").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val firestoreCart = document.toObject(FirestoreCart::class.java)
                    firestoreCart?.let {
                        val cartItems = it.items.map { cartItem ->
                            CartItem(
                                product = Product(
                                    id = cartItem.productId,
                                    name = "", // Será buscado mais tarde
                                    price = 0.0, // Será buscado mais tarde
                                    imageUrl = "" // Será buscado mais tarde
                                ),
                                quantity = cartItem.quantity
                            )
                        }
                        _cartItems.value = cartItems
                        loadProductDetails(cartItems)
                    }
                }
            }
            .addOnFailureListener { e ->
                println("Erro ao carregar o carrinho: ${e.message}")
            }
    }

    /**
     * Função para buscar detalhes dos produtos no Firestore.
     */
    private fun loadProductDetails(cartItems: List<CartItem>) {
        val firestore = FirebaseFirestore.getInstance()
        val updatedCartItems = cartItems.toMutableList()

        cartItems.forEachIndexed { index, item ->
            firestore.collection("products").document(item.product.id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val product = document.toObject(Product::class.java)
                        product?.let {
                            updatedCartItems[index] = item.copy(
                                product = it
                            )
                            _cartItems.value = updatedCartItems
                        }
                    }
                }
                .addOnFailureListener { e ->
                    println("Erro ao carregar detalhes do produto: ${e.message}")
                }
        }
    }

    /**
     * Função para salvar o carrinho no Firestore.
     */
    fun saveCartToFirestore(userId: String) {
        val firestore = FirebaseFirestore.getInstance()

        val firestoreCartItems = _cartItems.value.map { cartItem ->
            FirestoreCartItem(
                productId = cartItem.product.id,
                quantity = cartItem.quantity,
                totalPrice = cartItem.totalPrice
            )
        }

        val firestoreCart = FirestoreCart(
            id = userId,
            userId = userId,
            items = firestoreCartItems,
            totalPrice = _cartItems.value.sumOf { it.totalPrice }
        )

        firestore.collection("carts").document(userId)
            .set(firestoreCart)
            .addOnSuccessListener {
                println("Carrinho salvo com sucesso!")
            }
            .addOnFailureListener { e ->
                println("Erro ao salvar o carrinho: ${e.message}")
            }
    }
}