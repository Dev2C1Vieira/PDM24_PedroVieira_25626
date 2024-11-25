package com.pedro.newsletter.data.remote.model

data class NewsResponseDto(
    val status: String,
    val results: List<NewsDto>
)