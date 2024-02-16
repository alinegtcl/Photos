package com.luisitolentino.photos.domain.entity

data class Photo(
    val title: String,
    val thumbnailUrl: String,
    val image: String
) {
    override fun toString(): String {
        return title
    }
}