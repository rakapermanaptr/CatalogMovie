package com.android.catalogmovie.data.remote

import com.android.catalogmovie.base.BaseResponse
import com.android.catalogmovie.data.remote.model.Genres
import com.android.catalogmovie.data.remote.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    suspend fun getGenreList(): Response<Genres>

    @GET("discover/movie")
    suspend fun getDiscoveryMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int? = 1,
        @Query("perPage") perPage: Int? = 25,
    ): Response<BaseResponse<List<Movie>>>

}