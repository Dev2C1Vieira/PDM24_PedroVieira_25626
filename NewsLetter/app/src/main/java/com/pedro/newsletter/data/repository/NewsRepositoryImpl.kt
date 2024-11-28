package com.pedro.newsletter.data.repository

import com.pedro.newsletter.data.remote.api.NewsLetterApi
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.repository.NewsRepository

class NewsRepositoryImpl(private val api: NewsLetterApi) : NewsRepository {

    override suspend fun getNews(/* section: String */): List<News> {
        try {
            val response = api.getTopStories(/* section */)

            return response.results.map { it.toDomain() }.take(10)
        } catch (e: Exception) {
            throw Exception("Erro ao carregar notícias: ${e.message}")
        }
    }
}
