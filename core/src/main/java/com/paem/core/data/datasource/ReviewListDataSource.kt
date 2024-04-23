package com.paem.core.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.paem.core.data.remote.ApiService
import com.paem.core.entities.Review
import com.paem.core.utils.toReview

class ReviewListDatasource(
    private val movieId: Int,
    private val network: ApiService
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val position = params.key ?: 1
            val response =
                network.getReviews(movieId = movieId, page = position)
            LoadResult.Page(
                data = response.body()!!.results!!.map { it.toReview() },
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.body()!!.totalPages!! > position) position + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}