package com.luisitolentino.photos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisitolentino.photos.domain.usecase.PhotosUseCase
import com.luisitolentino.photos.domain.utils.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotosViewModel(private val useCase: PhotosUseCase) : ViewModel() {

    private val _state = MutableStateFlow<PhotoState>(PhotoState.HideLoading)
    val state = _state.asStateFlow()

    fun retrievePhotosList() {
        viewModelScope.launch {
            _state.value = PhotoState.ShowLoading
            val result = useCase.retrievePhotosList()
            result.flow({ photoList ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.PhotoSuccess(photoList)
            }, { errorMessage ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.PhotoError(errorMessage)
            })
        }
    }

    fun retrieveImage(imageUrl: String) {
        _state.value = PhotoState.ShowLoading
        viewModelScope.launch {
            val result = useCase.retrievePhoto(imageUrl)
            result.flow({ image ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.ImageSuccess(image)
            }, { errorMessage ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.PhotoError(errorMessage)
            })
        }
//        ImageRequest(
//            imageUrl,
//            { image ->
//                _state.value = PhotoState.HideLoading
//                _state.value = PhotoState.ImageSuccess(image)
//            },
//            0,
//            0,
//            ImageView.ScaleType.CENTER,
//            Bitmap.Config.ARGB_8888,
//            {
//                _state.value = PhotoState.HideLoading
//                _state.value = PhotoState.PhotoError(it.message ?: "deu ruim")
//            }).also {
//            PhotosService.getInstance(photosActivity).addToRequestQueue(it)
//        }
    }

    fun retrieveThumbnail(imageUrl: String) {
        _state.value = PhotoState.ShowLoading
        viewModelScope.launch {
            val result = useCase.retrievePhoto(imageUrl)
            result.flow({ image ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.ThumbnailSuccess(image)
            }, { errorMessage ->
                _state.value = PhotoState.HideLoading
                _state.value = PhotoState.PhotoError(errorMessage)
            })
        }
//        ImageRequest(
//            imageUrl,
//            { image ->
//                _state.value = PhotoState.HideLoading
//                _state.value = PhotoState.ThumbnailSuccess(image)
//            },
//            0,
//            0,
//            ImageView.ScaleType.CENTER,
//            Bitmap.Config.ARGB_8888,
//            {
//                _state.value = PhotoState.HideLoading
//                _state.value = PhotoState.PhotoError(it.message ?: "deu ruim")
//            }).also {
//            PhotosService.getInstance(photosActivity).addToRequestQueue(it)
//        }
    }

}