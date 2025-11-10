package com.paem.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import org.koin.core.component.KoinComponent
import androidx.lifecycle.viewModelScope as scope

abstract class BaseViewModel : ViewModel(), KoinComponent {
    val viewModelScope = scope + CoroutineExceptionHandler { coroutineContext, exception ->
        println("Caught $exception, $coroutineContext")
        exception.printStackTrace()
        throw exception
    }
    val ioVmCorContext = viewModelScope.coroutineContext + Dispatchers.IO
}