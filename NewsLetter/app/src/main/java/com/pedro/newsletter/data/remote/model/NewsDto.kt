package com.pedro.newsletter.data.remote.model

import com.pedro.newsletter.domain.model.News

data class NewsDto (
    val title: String,
    val abstract: String,
    val url: String,
    val multimedia: List<MultimediaDto>?
)

fun NewsDto.toDomain(): News {
    return News(
        title = this.title,
        summary = this.abstract,
        url = this.url
    )
}
