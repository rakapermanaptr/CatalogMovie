package com.android.catalogmovie.data.remote

import com.android.catalogmovie.data.remote.model.Genres
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("genre/movie/list")
    suspend fun getGenreList(): Response<Genres>

}