package com.luisitolentino.photos.domain.usecase

import android.graphics.Bitmap
import com.luisitolentino.photos.domain.entity.Photo
import com.luisitolentino.photos.domain.utils.PhotoResult

interface PhotosUseCase {
    suspend fun retrievePhotosList() : PhotoResult<List<Photo>, String>
    suspend fun retrievePhoto(imageUrl: String) : PhotoResult<Bitmap, String>
}