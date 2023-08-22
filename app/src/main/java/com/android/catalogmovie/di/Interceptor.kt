package com.android.catalogmovie.di

import com.android.catalogmovie.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.let {
            val original = chain.request()
            val originalUrl = original.url
            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val requestBuilder = original.newBuilder().url(url)
            return chain.proceed(requestBuilder.build())
        }
    }

}