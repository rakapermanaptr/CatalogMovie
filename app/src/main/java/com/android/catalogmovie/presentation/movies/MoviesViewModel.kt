package com.android.catalogmovie.presentation.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.catalogmovie.base.BaseViewModel
import com.android.catalogmovie.data.MovieRepository
import com.android.catalogmovie.data.remote.model.Movie

class MoviesViewModel : BaseViewModel() {
    private val repo = MovieRepository()

    fun getMovieList(genreId: Int): LiveData<PagingData<Movie>> {
        return repo.getAllMovies(genreId).cachedIn(viewModelScope)
    }

}