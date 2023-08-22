package com.android.catalogmovie.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.android.catalogmovie.data.remote.model.Movie
import kotlinx.coroutines.CoroutineScope

class MoviesFactory(
    private val genreId: Int,
    private val repo: MovieRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {

    val source = MutableLiveData<MoviesDatasource>()

    override fun create(): DataSource<Int, Movie> {
        val ds = MoviesDatasource(genreId, repo, scope)
        source.postValue(ds)
        return ds
    }
}