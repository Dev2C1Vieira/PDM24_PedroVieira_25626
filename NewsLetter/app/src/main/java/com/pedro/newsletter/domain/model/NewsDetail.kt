package com.pedro.newsletter.domain.model

// The NewsDetail class will be used to display the details of a specific article.
data class NewsDetail(
    val url: String,                    // Link to the full article
    val author: String,                 // Author's name
    val updatedDate: String,            // Date when the article was last updated
    val createdDate: String,            // Date when the article was created
    val publishedDate: String           // Date when the article was published
)