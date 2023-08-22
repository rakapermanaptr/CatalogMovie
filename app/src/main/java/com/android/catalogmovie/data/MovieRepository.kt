package com.android.catalogmovie.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.android.catalogmovie.base.BaseRepository
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
}