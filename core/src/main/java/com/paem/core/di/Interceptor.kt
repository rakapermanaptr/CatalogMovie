package com.paem.core.di

import okhttp3.Interceptor
import okhttp3.Response

class Interceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.let {
            val original = chain.request()
            val originalUrl = original.url
            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", "6d90617eed798d4678c51320e816bae1")
                .build()

            val requestBuilder = original.newBuilder().url(url)
            return chain.proceed(requestBuilder.build())
        }
    }

}