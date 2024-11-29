package com.pedro.newsletter.presentation.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.newsletter.data.remote.api.RetrofitInstance
import com.pedro.newsletter.data.repository.NewsRepositoryImpl
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.use_case.GetNewsDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsDetailListViewModel : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsDetailUseCase = GetNewsDetailUseCase(repository)


    private val _newsDetail = MutableStateFlow<News?>(null)
    val newsDetail = _newsDetail

    fun fetchNewsDetail(section: String, newsURL: String) {
        viewModelScope.launch {
            try {
                _newsDetail.value = getNewsDetailUseCase(section,  newsURL)
            } catch (e: Exception) {
                _newsDetail.value = null
            }
        }
    }
}
