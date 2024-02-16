package com.luisitolentino.photos

import android.app.Application
import com.luisitolentino.photos.framework.di.appPhotosModules
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PhotosApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@PhotosApplication)
            modules(appPhotosModules)
        }
    }
}