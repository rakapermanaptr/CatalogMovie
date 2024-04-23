package com.android.catalogmovie.presentation.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paem.core.data.remote.RequestState
import com.paem.core.entities.Genre
import com.paem.core.utils.toGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel : ViewModel() {
    private val repo = com.paem.core.data.MovieRepository()

    fun getGenres(
        callback: (state: RequestState<List<Genre>>) -> Unit
    ) {
        callback(RequestState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val process = repo.getGenres()
            launch(Dispatchers.Main) {
                if (process is com.paem.core.data.remote.ProcessState.Success) {
                    val genres = process.result.genres
                    if (genres != null) {
                        callback(RequestState.Success(genres.map { it.toGenre() }))
                    } else {
                        callback(RequestState.Success(emptyList()))
                    }
                } else if (process is com.paem.core.data.remote.ProcessState.Failed) {
                    callback(RequestState.Failed(process.error))
                }
            }
        }
    }

}