package com.pedro.newsletter.domain.repository

import com.pedro.newsletter.domain.model.News

interface NewsRepository {
    suspend fun getNews( section: String ): List<News>
}