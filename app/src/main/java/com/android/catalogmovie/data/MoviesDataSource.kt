package com.android.catalogmovie.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.catalogmovie.base.BaseResponse
import com.android.catalogmovie.data.remote.NetworkState
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.data.remote.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesDatasource(
    private val genreId: Int,
    private val repo: MovieRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {

    val refreshState = MutableLiveData<NetworkState<BaseResponse<List<Movie>>>>()
    val loadState = MutableLiveData<NetworkState<BaseResponse<List<Movie>>>>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        refreshState.postValue(NetworkState.Loading)
        scope.launch(Dispatchers.IO) {
            val state = repo.getMoviesPaging(
                genreId = genreId,
                page = 1,
                perPage = params.requestedLoadSize
            )

            if (state is RequestState.Success) {
                refreshState.postValue(NetworkState.Success(state.result))
                val movies = state.result.results ?: emptyList()
                callback.onResult(movies, 0, 2)
            } else if (state is RequestState.Failed) {
                refreshState.postValue(state.error)
            }
        }

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        loadState.postValue(NetworkState.Loading)
        scope.launch(Dispatchers.IO) {
            val page = params.key
            val state = repo.getMoviesPaging(
                genreId = genreId,
                page = 1,
                perPage = params.requestedLoadSize
            )
            if (state is RequestState.Success) {
                loadState.postValue(NetworkState.Success(state.result))
                val movies = state.result.results ?: emptyList()
                callback.onResult(movies, page + 1)
            } else if (state is RequestState.Failed) {
                loadState.postValue(state.error)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
    }


}