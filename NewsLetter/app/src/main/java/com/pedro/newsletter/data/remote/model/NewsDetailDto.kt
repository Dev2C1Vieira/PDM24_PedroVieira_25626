package com.pedro.newsletter.data.remote.model

import com.pedro.newsletter.domain.model.NewsDetail

data class NewsDetailDto (
    val updated_date: String,
    val created_date: String,
    val published_date: String,
) {
    fun toDomain(): NewsDetail {
        return NewsDetail(
            updatedDate = this.updated_date,
            createdDate = this.created_date,
            publishedDate = this.published_date,
        )
    }
}