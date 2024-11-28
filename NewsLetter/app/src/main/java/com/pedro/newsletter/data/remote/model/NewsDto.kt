package com.pedro.newsletter.data.remote.model

import com.pedro.newsletter.domain.model.News

data class NewsDto(
    val title: String,
    val abstract: String,            // Same as summary
    val byline: String,             // Same as author
    val updated_date: String,
    val created_date: String,
    val published_date: String,
    val url: String,
    var multimedia: List<Multimedia>?
) {
    fun toDomain(): News {

        val imageURL = if (multimedia != null && multimedia!!.size > 1) {
            multimedia!![1].url
        } else {
            "https://via.assets.so/img.jpg?w=400&h=150&tc=blue&bg=#cecece"
        }

        println("Image URL: $imageURL")

        return News(
            title = this.title,
            summary = this.abstract,
            author = this.byline,
            updatedDate = this.updated_date,
            createdDate = this.created_date,
            publishedDate = this.published_date,
            url = this.url,
            image_url = imageURL
        )
    }
}

data class Multimedia(
    val url: String,
)