package com.luisitolentino.photos.domain.utils

sealed class PhotoResult<out S, out E> {
    data class Success<out S>(val data: S) : PhotoResult<S, Nothing>()
    data class Error<out E>(val failure: E) : PhotoResult<Nothing, E>()
}

inline fun <S, E, R> PhotoResult<S, E>.flow(
    success: (S) -> R,
    error: (E) -> R
): R {
    return when (this) {
        is PhotoResult.Success -> success(data)
        is PhotoResult.Error -> error(failure)
    }
}