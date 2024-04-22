package com.android.catalogmovie.presentation.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.catalogmovie.data.MovieRepository
import com.android.catalogmovie.data.remote.ProcessState
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.domain.entities.MovieDetail
import com.android.catalogmovie.domain.entities.Review
import com.android.catalogmovie.domain.entities.Video
import com.android.catalogmovie.utils.toMovieDetail
import com.android.catalogmovie.utils.toVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private val repo = MovieRepository()

    fun getMovieDetails(
        movieId: Int,
        callback: (state: RequestState<MovieDetail>) -> Unit
    ) {
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getMovieDetails(movieId)
            launch(Dispatchers.Main) {
                if (process is ProcessState.Success) {
                    callback(RequestState.Success(process.result.toMovieDetail()))
                } else if (process is ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

    fun getReviews(movieID: Int): LiveData<PagingData<Review>> {
        return repo.getReviews(movieID).cachedIn(viewModelScope)
    }

    fun getVideos(
        movieId: Int,
        callback: (state: RequestState<List<Video>>) -> Unit
    ) {
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getVideos(movieId)
            launch(Dispatchers.Main) {
                if (process is ProcessState.Success) {
                    val videos = process.result.results
                    if (videos != null) callback(RequestState.Success(videos.map { it.toVideo() }))
                    else callback(RequestState.Success(emptyList()))
                } else if (process is ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

    private var _isReadMore = MutableLiveData<Boolean>()
    val isReadMore get() = _isReadMore

    fun setReadMore(more: Boolean) {
        _isReadMore.value = more
    }

}