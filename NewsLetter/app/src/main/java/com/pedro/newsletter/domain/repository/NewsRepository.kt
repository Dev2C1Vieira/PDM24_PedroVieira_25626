package com.pedro.newsletter.domain.repository

import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.model.NewsDetail

interface NewsRepository {
    suspend fun getNews(section: String, apiKey: String): List<News>
    suspend fun getNewsDetail(newsId: String, apiKey: String): NewsDetail
}