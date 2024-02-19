package com.luisitolentino.photos.framework.di

import com.luisitolentino.photos.data.repository.PhotosRepositoryImpl
import com.luisitolentino.photos.domain.usecase.PhotosUseCase
import com.luisitolentino.photos.framework.datasource.PhotosService
import com.luisitolentino.photos.presentation.viewmodel.PhotosViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appPhotosModules = module {
    single { PhotosService(androidContext()) }
    factory<PhotosUseCase> { PhotosRepositoryImpl(get()) }
    viewModel { PhotosViewModel(get()) }
}