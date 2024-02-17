package com.luisitolentino.photos.presentation.viewmodel

import android.graphics.Bitmap
import com.luisitolentino.photos.domain.entity.Photo

sealed class PhotoState {
    object HideLoading : PhotoState()
    object ShowLoading : PhotoState()
    data class PhotoSuccess(val list: List<Photo>) : PhotoState()
    data class ImageSuccess(val image: Bitmap) : PhotoState()
    data class ThumbnailSuccess(val image: Bitmap) : PhotoState()
    data class PhotoError(val error: String) : PhotoState()
}