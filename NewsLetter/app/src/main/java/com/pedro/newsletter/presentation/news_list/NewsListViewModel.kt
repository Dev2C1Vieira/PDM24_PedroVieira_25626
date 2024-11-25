package com.pedro.newsletter.presentation.news_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.use_case.GetNewsUseCase
import kotlinx.coroutines.launch

class NewsListViewModel(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> get() = _newsList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchNews(section: String, apiKey: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val news = getNewsUseCase(section, apiKey)
                _newsList.value = news
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar notícias: ${e.message}"
                _isLoading.value = false
            }
        }
    }
}
