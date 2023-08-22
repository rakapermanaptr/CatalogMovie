package com.android.catalogmovie.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.android.catalogmovie.base.BaseRepository
import com.android.catalogmovie.data.datasource.MoviesListDatasource
import com.android.catalogmovie.data.datasource.ReviewListDatasource
import com.android.catalogmovie.data.remote.NetworkState
import com.android.catalogmovie.data.remote.ProcessState
import com.android.catalogmovie.data.remote.model.*
import com.android.catalogmovie.data.remote.stateNetworkCall
import com.android.catalogmovie.domain.entities.Movie
import com.android.catalogmovie.domain.entities.Review

class MovieRepository : BaseRepository() {

    suspend fun getGenres(): ProcessState<Genres> {
        val response =
            stateNetworkCall { network.getGenreList() }
        return if (response is NetworkState.Success) {
            ProcessState.Success(response.result)
        } else {
            ProcessState.Failed(response as NetworkState.Failed)
        }
    }

    fun getMoviesByGenre(genreId: Int): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviesListDatasource(genreId, network)
            }
            , initialKey = 1
        ).liveData
    }

    suspend fun getMovieDetails(movieId: Int): ProcessState<MovieDetailsResponse> {
        val response =
            stateNetworkCall { network.getMovieDetails(movieId) }
        return if (response is NetworkState.Success) {
            ProcessState.Success(response.result)
        } else {
            ProcessState.Failed(response as NetworkState.Failed)
        }
    }

    fun getReviews(movieId: Int): LiveData<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                ReviewListDatasource(movieId, network)
            }
            , initialKey = 1
        ).liveData
    }

    suspend fun getVideos(movieId: Int): ProcessState<VideosResponse> {
        val response =
            stateNetworkCall { network.getVideos(movieId) }
        return if (response is NetworkState.Success) {
            ProcessState.Success(response.result)
        } else {
            ProcessState.Failed(response as NetworkState.Failed)
        }
    }

}