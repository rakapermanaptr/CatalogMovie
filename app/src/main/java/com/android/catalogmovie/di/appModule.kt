package com.android.catalogmovie.di

import com.android.catalogmovie.CatalogMovieApp
import com.paem.core.data.remote.ApiService
import com.paem.core.di.Interceptor
import com.paem.core.di.makeRetrofitService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { androidApplication() as CatalogMovieApp }
    single { get<Retrofit>().create(ApiService::class.java) }
    single { makeRetrofitService(androidApplication()) }
    single { Interceptor() }
}
