package com.pedro.newsletter.domain.use_case

import com.pedro.newsletter.domain.model.NewsDetail
import com.pedro.newsletter.domain.repository.NewsRepository

class GetNewsDetailUseCase (private val repository: NewsRepository) {
    suspend operator fun invoke(newsId: String): NewsDetail {
        return repository.getNewsDetail(newsId)
    }
}