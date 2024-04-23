package com.paem.core.data.remote

import com.paem.core.utils.getErrorResponse
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> stateNetworkCall(retrofitCall: (suspend () -> Response<T>)): NetworkState<T> {
    return try {
        val response = retrofitCall.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkState.Success<T>(body)
        } else {
            if (response.errorBody()?.toString()?.contains("gateway time-out error") == true)
                NetworkState.Failed.ByTimeout
            else {
                val converterError = response.getErrorResponse()
                NetworkState.Failed.ByResponse(converterError, response.code())
            }
        }
    } catch (e: UnknownHostException) {
        NetworkState.Failed.NoConnection
    } catch (se3: SocketTimeoutException) {
        NetworkState.Failed.ByTimeout
    } catch (e: Throwable) {
        NetworkState.Failed.ByException(e)
    }
}
