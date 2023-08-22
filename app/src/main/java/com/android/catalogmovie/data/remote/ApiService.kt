package com.android.catalogmovie.data.remote

import com.android.catalogmovie.data.remote.model.Genres
import com.android.catalogmovie.data.remote.model.MovieDetailsResponse
import com.android.catalogmovie.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    suspend fun getGenreList(): Response<Genres>

    @GET("discover/movie")
    suspend fun getDiscoveryMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int? = 1,
        @Query("perPage") perPage: Int? = 25,
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailsResponse>
}