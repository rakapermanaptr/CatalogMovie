package com.android.catalogmovie.presentation.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.paem.core.base.BaseViewModel
import com.paem.core.data.MovieRepository
import com.paem.core.entities.Movie

class MoviesViewModel : BaseViewModel() {
    private val repo = MovieRepository()

    fun getMoviesByGenre(genreId: Int): LiveData<PagingData<Movie>> {
        return repo.getMoviesByGenre(genreId).cachedIn(viewModelScope)
    }

}