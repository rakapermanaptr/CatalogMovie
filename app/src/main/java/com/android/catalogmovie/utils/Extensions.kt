package com.android.catalogmovie.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.catalogmovie.data.remote.model.ErrorResponse
import com.android.catalogmovie.di.jsonConfiguration
import org.json.JSONObject
import retrofit2.Response

fun Response<*>.getErrorResponse(): ErrorResponse {
    val errorResponse = this.errorBody()?.string()
    return try {
        jsonConfiguration.decodeFromString(ErrorResponse.serializer(), errorResponse!!)
    } catch (e: Exception) {
        e.printStackTrace()
        val message = try {
            val obj = JSONObject(errorResponse!!)
            obj.optString("message", "${code()} ${message()}")
        } catch (e: Exception) {
            "${code()} ${message()}"
        }
        ErrorResponse(
            statusCode = code(),
            statusMessage = message
        )
    }
}