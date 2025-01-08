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
import com.google.firebase.firestore.Filter

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    val totalCartPrice: StateFlow<Double> = _cartItems.map { cartItems ->
        cartItems.sumOf { it.totalPrice }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    fun addToCart(userId: String, product: Product) {
        val firestore = FirebaseFirestore.getInstance()

        // Procurar a coleção onde o userId OU sharedWith contém o uid do user logado
        firestore.collection("carts")
            .where(Filter.or(
                Filter.equalTo("userId", userId),
                Filter.arrayContains("sharedWith", userId)
            ))
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Atualizar o carrinho encontrado
                    for (document in documents) {
                        val cartRef = firestore.collection("carts").document(document.id)
                        cartRef.get().addOnSuccessListener { cartDoc ->
                            val currentCartItems = _cartItems.value.toMutableList()
                            val existingItemIndex = currentCartItems.indexOfFirst { it.product.id == product.id }

                            if (existingItemIndex != -1) {
                                // Se o produto já existe, aumenta a quantidade
                                val updatedItem = currentCartItems[existingItemIndex].copy(
                                    quantity = currentCartItems[existingItemIndex].quantity + 1
                                )
                                currentCartItems[existingItemIndex] = updatedItem
                            } else {
                                // Se não existe, adiciona um novo item
                                currentCartItems.add(CartItem(product, quantity = 1))
                            }

                            _cartItems.value = currentCartItems

                            // Atualizar Firestore
                            val firestoreCartItems = currentCartItems.map {
                                FirestoreCartItem(
                                    productId = it.product.id,
                                    quantity = it.quantity,
                                    totalPrice = it.totalPrice
                                )
                            }
                            val totalCartPrice = currentCartItems.sumOf { it.totalPrice }

                            val updatedCart = FirestoreCart(
                                userId = userId,
                                items = firestoreCartItems,
                                totalPrice = totalCartPrice
                            )

                            cartRef.set(updatedCart)
                                .addOnSuccessListener {
                                    println("Carrinho atualizado com sucesso!")
                                }
                                .addOnFailureListener { e ->
                                    println("Erro ao atualizar o carrinho: ${e.message}")
                                }
                        }
                    }
                } else {
                    // Se nenhum carrinho encontrado, cria um novo
                    val cartRef = firestore.collection("carts").document(userId)
                    val newCart = FirestoreCart(
                        userId = userId,
                        items = listOf(FirestoreCartItem(product.id, 1, product.price)),
                        totalPrice = product.price
                    )

                    cartRef.set(newCart)
                        .addOnSuccessListener {
                            println("Novo carrinho criado com sucesso!")
                        }
                        .addOnFailureListener { e ->
                            println("Erro ao criar o carrinho: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                println("Erro ao procurar carrinho: ${e.message}")
            }
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
     * Carregar o carrinho do utilizador logado.
     * Agora usa o `userId` diretamente como o ID do documento.
     */
    fun loadCart(userId: String) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("carts")
            .where(
                Filter.or(
                    Filter.equalTo("userId", userId),
                    Filter.arrayContains("sharedWith", userId)
                )
            )
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents.first()  // Obtém o primeiro carrinho encontrado
                    val firestoreCart = document.toObject(FirestoreCart::class.java)

                    firestoreCart?.let { cart ->
                        val cartItems = cart.items.map { cartItem ->
                            CartItem(
                                product = Product(
                                    id = cartItem.productId,
                                    name = "",
                                    price = 0.0,
                                    imageUrl = ""
                                ),
                                quantity = cartItem.quantity
                            )
                        }
                        _cartItems.value = cartItems
                        loadProductDetails(cartItems)
                    }
                } else {
                    println("Nenhum carrinho encontrado para este utilizador.")
                    _cartItems.value = emptyList()
                }
            }
            .addOnFailureListener { e ->
                println("Erro ao carregar o carrinho: ${e.message}")
                _cartItems.value = emptyList()
            }
    }


    /**
     * Busca detalhes dos produtos no Firestore, preservando o productId.
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
                            updatedCartItems[index] = item.copy(product = it.copy(id = item.product.id))  // Mantendo o ID
                            if (index == cartItems.lastIndex) {
                                _cartItems.value = updatedCartItems
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    println("Erro ao carregar detalhes do produto: ${e.message}")
                }
        }
    }

    /**
     * Salvar o carrinho no Firestore.
     * Usa o ID do utilizador como identificador único.
     */
    fun saveCartToFirestore(userId: String) {
        val firestore = FirebaseFirestore.getInstance()

        val firestoreCartItems = _cartItems.value.map { cartItem ->
            FirestoreCartItem(
                productId = cartItem.product.id.ifBlank { "UNKNOWN_ID" },
                quantity = cartItem.quantity,
                totalPrice = cartItem.totalPrice
            )
        }

        // Procurar o carrinho onde o userId ou sharedWith contém o UID do user logado
        firestore.collection("carts")
            .where(Filter.or(
                Filter.equalTo("userId", userId),
                Filter.arrayContains("sharedWith", userId)
            ))
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Se não encontrar, cria um novo carrinho e define o userId
                    firestore.collection("carts").document(userId)
                        .set(FirestoreCart(
                            userId = userId, // Mantém o userId do próprio utilizador, pois está a criar um novo
                            items = firestoreCartItems,
                            totalPrice = _cartItems.value.sumOf { it.totalPrice }
                        ))
                        .addOnSuccessListener {
                            println("Novo carrinho criado com sucesso!")
                        }
                        .addOnFailureListener { e ->
                            println("Erro ao criar novo carrinho: ${e.message}")
                        }
                } else {
                    // Se encontrar, atualiza o carrinho existente sem modificar o userId
                    for (document in documents) {
                        val originalUserId = document.getString("userId") ?: userId
                        firestore.collection("carts").document(document.id)
                            .set(FirestoreCart(
                                userId = originalUserId,  // Mantém o userId original encontrado
                                items = firestoreCartItems,
                                totalPrice = _cartItems.value.sumOf { it.totalPrice },
                                sharedWith = document.get("sharedWith") as? List<String> ?: emptyList()
                            ))
                            .addOnSuccessListener {
                                println("Carrinho atualizado com sucesso!")
                            }
                            .addOnFailureListener { e ->
                                println("Erro ao atualizar o carrinho: ${e.message}")
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                println("Erro ao procurar carrinho: ${e.message}")
            }
    }
}