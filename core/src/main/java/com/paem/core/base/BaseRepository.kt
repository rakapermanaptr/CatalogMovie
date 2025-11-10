package com.paem.core.base

import com.paem.core.data.remote.ApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


abstract class BaseRepository: KoinComponent {
    val network: ApiService by inject()
}