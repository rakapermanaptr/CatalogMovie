package com.paem.core.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.paem.core.data.remote.ApiService
import com.paem.core.entities.Movie
import com.paem.core.utils.toMovie

class MoviesListDatasource(
    private val genreId: Int,
    private val network: ApiService
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val response =
                network.getDiscoveryMovies(genreId = genreId, page = position, perPage = 25)
            LoadResult.Page(
                data = response.body()!!.results.map { it.toMovie() },
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}