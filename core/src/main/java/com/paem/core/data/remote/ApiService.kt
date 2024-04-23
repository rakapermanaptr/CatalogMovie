package com.paem.core.data.remote

import com.paem.core.data.remote.model.Genres
import com.paem.core.data.remote.model.MovieDetailsResponse
import com.paem.core.data.remote.model.MoviesResponse
import com.paem.core.data.remote.model.ReviewsResponse
import com.paem.core.data.remote.model.VideosResponse
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
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailsResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int? = 1,
    ): Response<ReviewsResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Int,
    ): Response<VideosResponse>
}