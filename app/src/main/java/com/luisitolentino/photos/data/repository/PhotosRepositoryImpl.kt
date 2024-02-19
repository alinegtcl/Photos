package com.luisitolentino.photos.data.repository

import android.graphics.Bitmap
import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import com.luisitolentino.photos.domain.entity.Photo
import com.luisitolentino.photos.domain.usecase.PhotosUseCase
import com.luisitolentino.photos.domain.utils.PhotoResult
import com.luisitolentino.photos.framework.datasource.PhotoDataSource
import com.luisitolentino.photos.framework.datasource.PhotosService

class PhotosRepositoryImpl(private val service: PhotosService) : PhotosUseCase {

    override suspend fun retrievePhotosList(): PhotoResult<List<Photo>, String> {
        var listResult: List<Photo> = listOf()

        PhotoDataSource({
            listResult = it
        }, { }).also {
            service.addToRequestQueue(it)
        }

        // Neste ponto a lista ainda está vazia
        // necessário repensar essa parte do fluxo para que a requisição seja finalizada
        // está retornando falha
        return if (listResult.isNotEmpty())
            PhotoResult.Success(listResult)
        else
            PhotoResult.Error("request failed")

    }

    override suspend fun retrievePhoto(imageUrl: String): PhotoResult<Bitmap, String> {
        var image: Bitmap? = null
        ImageRequest(
            imageUrl,
            {
                //retornar sucesso
                image = it
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                //retornar erro
            }).also {
            service.addToRequestQueue(it)
        }

        return if (image != null)
            PhotoResult.Success(image!!)
        else PhotoResult.Error("request failed")
    }

}