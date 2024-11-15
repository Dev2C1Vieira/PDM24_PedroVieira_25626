package com.pedro.newsletter.domain.use_case

import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.repository.NewsRepository

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): List<News> {
        return repository.getNews()
    }
}