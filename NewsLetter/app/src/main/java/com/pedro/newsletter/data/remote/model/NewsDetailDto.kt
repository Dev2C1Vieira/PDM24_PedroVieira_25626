package com.pedro.newsletter.data.remote.model

import com.pedro.newsletter.domain.model.NewsDetail

data class NewsDetailDto (
    val id: String,
    val name: String,
    val description: String
) {
    fun toNewsDetail(): NewsDetail {
        return NewsDetail(id = id, name = name, description = description)
    }
}