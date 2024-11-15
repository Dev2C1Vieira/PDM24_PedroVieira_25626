package com.pedro.newsletter.data.remote.api

import com.pedro.newsletter.data.remote.model.NewsResponseDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitInstance {
    val api: NewsLetterApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/home.json")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsLetterApi::class.java)
    }
}

interface NewsLetterApi {
    @GET("{category}.json")
    suspend fun getTopStories(
        @Path("category") category: String,
        @Query("api-key") apiKey: String
    ): Response<NewsResponseDto>

}