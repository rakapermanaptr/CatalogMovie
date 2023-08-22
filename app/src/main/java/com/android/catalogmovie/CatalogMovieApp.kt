package com.android.catalogmovie

import android.app.Application
import com.android.catalogmovie.di.appModule
import com.android.catalogmovie.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CatalogMovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CatalogMovieApp)
            modules(listOf(appModule, vmModule))
        }
    }
}