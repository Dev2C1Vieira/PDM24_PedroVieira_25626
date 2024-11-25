package com.pedro.newsletter.presentation.news_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pedro.newsletter.domain.model.NewsDetail
import com.pedro.newsletter.domain.use_case.GetNewsDetailUseCase
import kotlinx.coroutines.launch

class NewsDetailListViewModel(private val getNewsDetailUseCase: GetNewsDetailUseCase) : ViewModel() {

    private val _newsDetail = MutableLiveData<NewsDetail>()
    val newsDetail: LiveData<NewsDetail> get() = _newsDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchNewsDetail(newsId: String, apiKey: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newsDetail = getNewsDetailUseCase(newsId, apiKey)
                _newsDetail.value = newsDetail
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar detalhes da notícia: ${e.message}"
                _isLoading.value = false
            }
        }
    }
}
