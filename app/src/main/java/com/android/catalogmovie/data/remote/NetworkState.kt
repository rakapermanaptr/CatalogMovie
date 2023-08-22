package com.android.catalogmovie.data.remote

import com.android.catalogmovie.data.remote.model.ErrorResponse


sealed class NetworkState<out T> {
    class Success<T>(val result: T) : NetworkState<T>()
    sealed class Failed : NetworkState<Nothing>() {

        class ByException(val t: Throwable) : Failed()
        class ByErrorMessage(val msg: String) : Failed()
        class ByResponse(val response: ErrorResponse, val code: Int = 0) : Failed()
        object ByTimeout : Failed()
        object NoConnection : Failed()
    }
}
