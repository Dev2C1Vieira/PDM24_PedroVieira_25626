package com.pedro.newsletter.domain.model

// The News class is used to list articles in the interface (e.g. list of articles).
data class News(
    val section: String,            // Main category (e.g., movies, business)
    val subsection: String?,        // Subcategory, if available
    val title: String,              // Article title
    val summary: String,            // Summary or description
    val url: String,                // Link to the full article
    val author: String              // Author's name
)