package com.pedro.newsletter.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class SharedViewModel : ViewModel() {
    var selectedCategory by mutableStateOf("home") // Categoria inicial

    // Função para atualizar a categoria selecionada
    fun updateCategory(category: String) {
        selectedCategory = category
    }
}