package com.android.catalogmovie.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import org.koin.core.KoinComponent
import androidx.lifecycle.viewModelScope as scope

abstract class BaseViewModel : ViewModel(), KoinComponent {
    val viewModelScope = scope + CoroutineExceptionHandler { coroutineContext, exception ->
        println("Caught $exception, $coroutineContext")
        exception.printStackTrace()
        throw exception
    }
}