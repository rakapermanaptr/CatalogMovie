package com.android.catalogmovie.di

import android.app.Application
import com.android.catalogmovie.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.getKoin
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val jsonConfiguration =
    Json(Json) { encodeDefaults = false; ignoreUnknownKeys = true; isLenient = true }

fun makeRetrofitService(
    application: Application,
): Retrofit {
    val koin = application.getKoin()

    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .readTimeout(180, TimeUnit.SECONDS)
        .connectTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .addInterceptor(logger)
        .addInterceptor(koin.get<Interceptor>())
        .build()

    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(jsonConfiguration.asConverterFactory(contentType))
        .build()
}