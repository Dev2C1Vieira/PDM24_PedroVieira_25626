package com.pedro.newsletter.data.remote.model

import com.pedro.newsletter.domain.model.News

data class NewsDto(
    val section: String,
    val subsection: String?,
    val title: String,
    val abstract: String,       // Same as summary
    val url: String,
    val byline: String          // Same as author
) {
    fun toDomain(): News {
        return News(
            section = this.section,
            subsection = this.subsection ?: "",
            title = this.title,
            summary = this.abstract,
            url = this.url,
            author = this.byline
        )
    }
}



