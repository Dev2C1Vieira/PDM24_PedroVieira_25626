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

class NewsListViewModel(/* section: String */) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsUseCase = GetNewsUseCase(repository)

    private val _news = MutableStateFlow<List<News>>(emptyList())

    val news: StateFlow<List<News>> = _news

    init {
        fetchNews(/* section */)
    }

    private fun fetchNews(/* section: String */) {
        viewModelScope.launch {
            try {
                _news.value = getNewsUseCase(/* section */)
            } catch (e: Exception) {
                _news.value = emptyList()
            }
        }
    }

}
