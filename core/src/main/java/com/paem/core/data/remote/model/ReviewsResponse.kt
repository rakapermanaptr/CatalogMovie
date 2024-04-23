package com.paem.core.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("page")
    val page: Int? = null,
    @SerialName("results")
    val results: List<Review>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
) {
    @Serializable
    data class Review(
        @SerialName("author")
        val author: String? = null,
        @SerialName("author_details")
        val authorDetails: AuthorDetails? = null,
        @SerialName("content")
        val content: String? = null,
        @SerialName("created_at")
        val createdAt: String? = null,
        @SerialName("id")
        val id: String,
        @SerialName("updated_at")
        val updatedAt: String? = null,
        @SerialName("url")
        val url: String? = null
    ) {
        @Serializable
        data class AuthorDetails(
            @SerialName("avatar_path")
            val avatarPath: String? = null,
            @SerialName("name")
            val name: String? = null,
            @SerialName("rating")
            val rating: Double? = null,
            @SerialName("username")
            val username: String? = null
        )
    }
}