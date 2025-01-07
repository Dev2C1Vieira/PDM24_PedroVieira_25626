package com.pedro.ecommerce.viewmodel.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.ecommerce.data.model.Product
import com.pedro.ecommerce.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productState = MutableStateFlow<List<Product>>(emptyList())
    val productState: StateFlow<List<Product>> = _productState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            val products = repository.fetchProducts()
            _productState.value = products
        }
    }
}