package com.android.catalogmovie.base

import com.android.catalogmovie.data.remote.ApiService
import org.koin.core.KoinComponent
import org.koin.core.inject


abstract class BaseRepository: KoinComponent {
    val network: ApiService by inject()
}