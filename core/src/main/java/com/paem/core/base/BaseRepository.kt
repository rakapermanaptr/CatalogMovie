package com.paem.core.base

import com.paem.core.data.remote.ApiService
import org.koin.core.KoinComponent
import org.koin.core.inject


abstract class BaseRepository: KoinComponent {
    val network: ApiService by inject()
}