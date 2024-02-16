package com.luisitolentino.photos.data.model

import com.luisitolentino.photos.domain.entity.Photo

class PhotoListResponse : ArrayList<PhotoResponse>() {
    fun toPhotoList(): List<Photo> {
        return this.map {photoResponse ->
            photoResponse.toPhoto()
        }
    }
}