package com.android.catalogmovie.presentation.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.catalogmovie.data.MovieRepository
import com.android.catalogmovie.data.remote.ProcessState
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.domain.entities.Genre
import com.android.catalogmovie.utils.toGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel : ViewModel() {
    private val repo = MovieRepository()

    fun getGenres(
        callback: (state: RequestState<List<Genre>>) -> Unit
    ) {
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getGenres()
            launch(Dispatchers.Main) {
                if (process is ProcessState.Success) {
                    val genres = process.result.genres
                    if (genres != null) {
                        callback(RequestState.Success(genres.map { it.toGenre() }))
                    } else {
                        callback(RequestState.Success(emptyList()))
                    }
                } else if (process is ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

}