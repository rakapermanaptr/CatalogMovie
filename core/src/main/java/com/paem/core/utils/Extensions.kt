package com.paem.core.utils

import com.paem.core.data.remote.model.ErrorResponse
import com.paem.core.di.jsonConfiguration
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