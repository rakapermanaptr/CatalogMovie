package com.android.catalogmovie.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.catalogmovie.data.remote.NetworkState

data class Listing<T: Any, R> constructor(
        // the LiveData of paged lists for the UI to observe
        val pagedList: LiveData<PagedList<T>>,
        // represents the network request status to show to the user
        val loadMoreState: LiveData<NetworkState<R>>,
        // refreshes the whole data and fetches it from scratch.
        val refresh: () -> Unit,
        // represents the network request status to show to the user
        val refreshState: LiveData<NetworkState<R>>
)