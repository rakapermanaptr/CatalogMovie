package com.android.catalogmovie.presentation.movies

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.android.catalogmovie.base.BaseResponse
import com.android.catalogmovie.base.BaseViewModel
import com.android.catalogmovie.data.MovieRepository
import com.android.catalogmovie.data.MoviesFactory
import com.android.catalogmovie.data.remote.NetworkState
import com.android.catalogmovie.data.remote.ProcessState
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.data.remote.model.Movie
import com.android.catalogmovie.utils.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class MoviesViewModel(private val genreId: Int?) : BaseViewModel() {
    private val repo = MovieRepository()

    fun getMovies(
        genreId: Int?,
        callback: (state: RequestState<List<Movie>?>) -> Unit
    ) {
        if (genreId == null) return
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getMovies(genreId)
            launch(Dispatchers.Main) {
                if (process is ProcessState.Success) {
                    callback(RequestState.Success(process.result.results))
                } else if (process is ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

    val itemSize = MutableLiveData(0)
    fun setItemSize(value: Int) {
        itemSize.postValue(value)
    }

    val listing: LiveData<Listing<Movie, BaseResponse<List<Movie>>>> = liveData(ioVmCorContext) {
        if (genreId == null) return@liveData
        val dataSource = MoviesFactory(genreId, repo, viewModelScope)
        val perPage = 25
        val config = PagedList.Config.Builder()
            .setPageSize(perPage)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(perPage)
            .build()

        val livePagedList = LivePagedListBuilder(dataSource, config)
            .setInitialLoadKey(0)
            .build()

        val refreshTrigger = {
            dataSource.source.value?.invalidate()
            Unit
        }
        val listing = Listing(
            pagedList = livePagedList,
            refresh = refreshTrigger,
            loadMoreState = dataSource.source.switchMap {
                it.loadState
            },
            refreshState = dataSource.source.switchMap {
                it.refreshState
            }
        )
        emit(listing)
    }

    val refreshState = listing.switchMap {
        it.refreshState
    }
    val pagingList = listing.switchMap {
        it.pagedList
    }

    val renderState = combine(
        itemSize.asFlow(),
        refreshState.asFlow()
    ) { size, refresh ->
        if (refresh is NetworkState.Loading) {
            RenderState.Loading
        } else if (size == 0)
            RenderState.EmptyState
        else {
            RenderState.Loaded
        }
    }

    sealed class RenderState {
        object Loading : RenderState()
        object EmptyState : RenderState()
        object Loaded : RenderState()
    }

}