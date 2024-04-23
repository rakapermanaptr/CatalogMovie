package com.paem.core.data.remote

sealed class RequestState<out T> {
    object Loading : RequestState<Nothing>()
    class Failed(val error: NetworkState.Failed) : RequestState<Nothing>()
    class Success<T>(val result: T) : RequestState<T>()
}
