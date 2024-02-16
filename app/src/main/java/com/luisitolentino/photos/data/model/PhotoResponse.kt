package com.luisitolentino.photos.data.model

import com.luisitolentino.photos.domain.entity.Photo

data class PhotoResponse(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
) {
    fun toPhoto() : Photo{
        return Photo(title, thumbnailUrl, url)
    }
}