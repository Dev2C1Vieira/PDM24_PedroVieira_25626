package com.pedro.newsletter.domain.model

// The News class is used to list articles in the interface (e.g. list of articles).
data class News(
    val title: String,                  // Article title
    val summary: String,                // Summary or description
    val author: String,                 // Author's name
    val updatedDate: String,            // Date when the article was last updated
    val createdDate: String,            // Date when the article was created
    val publishedDate: String,          // Date when the article was published
    val url: String,                     // Link to the full article
    var image_url: String
)