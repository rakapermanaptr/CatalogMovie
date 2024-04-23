package com.paem.core.data.remote

sealed class ProcessState<out T> {
    class Failed(val error: NetworkState.Failed) : ProcessState<Nothing>()
    class Success<T>(val result: T) : ProcessState<T>()
    object Loading : ProcessState<Nothing>()
}
