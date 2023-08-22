package com.android.catalogmovie.data

import com.android.catalogmovie.base.BaseRepository
import com.android.catalogmovie.base.BaseResponse
import com.android.catalogmovie.data.remote.NetworkState
import com.android.catalogmovie.data.remote.ProcessState
import com.android.catalogmovie.data.remote.model.Genres
import com.android.catalogmovie.data.remote.model.Movie
import com.android.catalogmovie.data.remote.stateNetworkCall

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

    suspend fun getMovies(genreId: Int): ProcessState<BaseResponse<List<Movie>>> {
        val response =
            stateNetworkCall { network.getDiscoveryMovies(genreId) }
        return if (response is NetworkState.Success) {
            ProcessState.Success(response.result)
        } else {
            ProcessState.Failed(response as NetworkState.Failed)
        }
    }
}