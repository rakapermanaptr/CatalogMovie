package com.paem.core.entities

data class Review(
    val id: String,
    val review: String,
    val author: String,
    var isReadMore: Boolean = false
)
