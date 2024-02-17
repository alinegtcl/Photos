package com.luisitolentino.photos.framework.di

import com.luisitolentino.photos.presentation.viewmodel.PhotosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appPhotosModules = module {
    viewModel { PhotosViewModel() }
}