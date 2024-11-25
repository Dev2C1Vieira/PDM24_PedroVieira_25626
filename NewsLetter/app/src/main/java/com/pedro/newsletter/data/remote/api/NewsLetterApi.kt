package com.pedro.newsletter.data.remote.api

import com.pedro.newsletter.data.remote.model.NewsDetailDto
import com.pedro.newsletter.data.remote.model.NewsResponseDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitInstance {
    val api: NewsLetterApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsLetterApi::class.java)
    }
}

interface NewsLetterApi {
    @GET("{section}.json")
    suspend fun getTopStories(
        @Path("section") section: String,
        @Query("api-key") apiKey: String
    ): NewsResponseDto


    @GET("article/{newsId}.json")
    suspend fun getNewsDetail(
        @Path("newsId") newsId: String,
        @Query("api-key") apiKey: String
    ): NewsDetailDto
}