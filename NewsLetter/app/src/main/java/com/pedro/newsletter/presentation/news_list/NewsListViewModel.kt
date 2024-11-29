package com.pedro.newsletter.presentation.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.newsletter.data.remote.api.RetrofitInstance
import com.pedro.newsletter.data.repository.NewsRepositoryImpl
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.use_case.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.text.Typography.section

class NewsListViewModel : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsUseCase = GetNewsUseCase(repository)

    // StateFlows para gerenciar o estado das notícias e carregamento
    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news

    private val _isLoading = MutableStateFlow(false) // Estado de carregamento
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Carregar as notícias inicialmente com a categoria "home"
        fetchNews("home")
    }

    // Função para buscar notícias com base na categoria selecionada
    fun fetchNews(section: String) {
        // Setando o estado de carregamento como true antes de fazer a requisição
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Chama o UseCase para obter as notícias da categoria
                _news.value = getNewsUseCase(section)
            } catch (e: Exception) {
                // Em caso de erro, atribui uma lista vazia e loga o erro
                _news.value = emptyList()
                // Aqui você pode logar o erro ou informar o usuário sobre falhas de rede
            } finally {
                // Finaliza o estado de carregamento
                _isLoading.value = false
            }
        }
    }
}
