package com.android.catalogmovie.presentation.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.catalogmovie.data.MovieRepository
import com.android.catalogmovie.data.remote.ProcessState
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.data.remote.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private val repo = MovieRepository()

    fun getMovieDetails(
        movieId: Int,
        callback: (state: RequestState<MovieDetailsResponse>) -> Unit
    ) {
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getMovieDetails(movieId)
            launch(Dispatchers.Main) {
                if (process is ProcessState.Success) {
                    callback(RequestState.Success(process.result))
                } else if (process is ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

    fun getReviews(movieID: Int): LiveData<PagingData<ReviewsResponse.Review>> {
        return repo.getReviews(movieID).cachedIn(viewModelScope)
    }

    fun getVideos(
        movieId: Int,
        callback: (state: RequestState<List<VideosResponse.Video>>) -> Unit
    ) {
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getVideos(movieId)
            launch(Dispatchers.Main) {
                if (process is ProcessState.Success) {
                    callback(RequestState.Success(process.result.results ?: emptyList()))
                } else if (process is ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

}