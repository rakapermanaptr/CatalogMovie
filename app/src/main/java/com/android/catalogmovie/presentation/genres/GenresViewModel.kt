package com.android.catalogmovie.presentation.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.catalogmovie.data.remote.model.Genres
import com.android.catalogmovie.data.MovieRepository
import com.android.catalogmovie.data.remote.ProcessState
import com.android.catalogmovie.data.remote.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel : ViewModel() {
    private val repo = MovieRepository()

    fun getGenres(
        callback: (state: RequestState<Genres>) -> Unit
    ) {
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getGenres()
            launch(Dispatchers.Main) {
                if (process is ProcessState.Success) {
                    callback(RequestState.Success(process.result))
                } else if (process is ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

}