package com.luisitolentino.photos.presentation.viewmodel

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.android.volley.toolbox.ImageRequest
import com.luisitolentino.photos.data.repository.DummyJSONAPI
import com.luisitolentino.photos.presentation.activity.PhotosActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotosViewModel : ViewModel() {

    private val _state = MutableStateFlow<PhotoState>(PhotoState.HideLoading)
    val state = _state.asStateFlow()

    fun retrievePhotosList(photosActivity: PhotosActivity) {
        _state.value = PhotoState.ShowLoading
        DummyJSONAPI.PhotoListRequest({ photoList ->
            _state.value = PhotoState.HideLoading
            _state.value = PhotoState.PhotoSuccess(photoList)
        }, {
            _state.value = PhotoState.HideLoading
            _state.value = PhotoState.PhotoError(it.message ?: "deu ruim")
        }).also {
            DummyJSONAPI.getInstance(photosActivity).addToRequestQueue(it)
        }
    }

    fun retrieveImage(imageUrl: String, photosActivity: PhotosActivity) {
        _state.value = PhotoState.ShowLoading
        ImageRequest(
            imageUrl,
            { image ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.ImageSuccess(image)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.PhotoError(it.message ?: "deu ruim")
            }).also {
            DummyJSONAPI.getInstance(photosActivity).addToRequestQueue(it)
        }
    }

    fun retrieveThumbnail(imageUrl: String, photosActivity: PhotosActivity) {
        _state.value = PhotoState.ShowLoading
        ImageRequest(
            imageUrl,
            { image ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.ThumbnailSuccess(image)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.PhotoError(it.message ?: "deu ruim")
            }).also {
            DummyJSONAPI.getInstance(photosActivity).addToRequestQueue(it)
        }
    }

}