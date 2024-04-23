package com.paem.core.utils

import android.content.Context
import com.paem.core.R
import com.paem.core.data.remote.NetworkState
import com.paem.core.data.remote.model.ErrorResponse
import java.util.concurrent.CancellationException

fun Context.handleErrorState(
    state: NetworkState.Failed,
    onErrorResponse: ((msg: ErrorResponse?) -> Unit)? = null
) {
    when (state) {
        is NetworkState.Failed.ByException -> {
            val t = state.t
            t.printStackTrace()
            if (t is CancellationException) {
                println("coroutines cancelled")
            } else {
                showToast(t.message ?: getString(R.string.something_went_wrong))
            }
        }
        is NetworkState.Failed.ByErrorMessage -> {
            showToast(state.msg)
        }
        is NetworkState.Failed.ByResponse -> {
            if (onErrorResponse != null) {
                onErrorResponse(state.response)
            } else {
                when {
                    state.code == 401 -> {
                        showToast(state.response.getErrorMessage())
                    }
                    state.code == 400 -> {
                        showToast("${state.response.statusMessage}")
                    }
                    state.code >= 500 ->  {
                        showToast(getString(R.string.internal_server_error))
                    }
                    else -> showToast(state.response.getErrorMessage())
                }
            }
        }
        NetworkState.Failed.ByTimeout -> {
            showToast(getString(R.string.error_timeout))
        }
        NetworkState.Failed.NoConnection -> {
            showToast(getString(R.string.no_internet_connection))
        }
    }
}