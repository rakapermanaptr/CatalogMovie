package com.android.catalogmovie.presentation.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.catalogmovie.base.BaseViewModel
import com.android.catalogmovie.data.MovieRepository
import com.android.catalogmovie.domain.entities.Movie

class MoviesViewModel : BaseViewModel() {
    private val repo = MovieRepository()

    fun getMoviesByGenre(genreId: Int): LiveData<PagingData<Movie>> {
        return repo.getMoviesByGenre(genreId).cachedIn(viewModelScope)
    }

}