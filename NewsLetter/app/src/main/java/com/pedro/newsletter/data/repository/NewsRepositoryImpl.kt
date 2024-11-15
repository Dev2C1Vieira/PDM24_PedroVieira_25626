package com.pedro.newsletter.data.repository

import android.util.Log
import com.pedro.newsletter.data.remote.api.NewsLetterApi
import com.pedro.newsletter.domain.model.News
import com.pedro.newsletter.domain.repository.NewsRepository

class NewsRepositoryImpl (private val api: NewsLetterApi): NewsRepository {
    override suspend fun getNews(category: String): List<News> {
        return try {
            val response = api.getTopStories(category, API_KEY)
            if (response.isSuccessful) {
                val newsList = response.body()?.results?.map { it.toDomain() } ?: emptyList()
                Log.d("API_SUCCESS", "Dados recebidos: $newsList")
                newsList
            } else {
                Log.e("API_ERROR", "Erro na resposta: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API_EXCEPTION", "Exceção na chamada da API: ${e.message}")
            emptyList()
        }
    }
}