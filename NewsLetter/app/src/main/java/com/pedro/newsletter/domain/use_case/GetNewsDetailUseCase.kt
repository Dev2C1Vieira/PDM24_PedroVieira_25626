package com.pedro.newsletter.domain.use_case

import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.repository.NewsRepository

class GetNewsDetailUseCase(private val repository: NewsRepository) {

    suspend operator fun invoke(section: String, newsURL: String): News? {

        val news = repository.getNews(section)

        return news.find { it.url == newsURL }
    }
}
