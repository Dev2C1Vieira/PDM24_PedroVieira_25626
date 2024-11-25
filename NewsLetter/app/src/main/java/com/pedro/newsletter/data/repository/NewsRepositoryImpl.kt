package com.pedro.newsletter.data.repository

import com.pedro.newsletter.data.remote.api.NewsLetterApi
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.model.NewsDetail
import com.pedro.newsletter.domain.repository.NewsRepository
import com.pedro.newsletter.data.remote.model.NewsDetailDto
import com.pedro.newsletter.data.remote.model.NewsResponseDto

class NewsRepositoryImpl(private val api: NewsLetterApi) : NewsRepository {

    override suspend fun getNews(section: String, apiKey: String): List<News> {
        try {
            val response: NewsResponseDto = api.getTopStories(section, apiKey)

            return response.results.map { it.toDomain() }
        } catch (e: Exception) {
            throw Exception("Erro ao carregar notícias: ${e.message}")
        }
    }

    override suspend fun getNewsDetail(newsId: String, apiKey: String): NewsDetail {
        try {
            val response: NewsDetailDto = api.getNewsDetail(newsId, apiKey)

            return response.toDomain()
        } catch (e: Exception) {
            throw Exception("Erro ao carregar detalhes da notícia: ${e.message}")
        }
    }
}
