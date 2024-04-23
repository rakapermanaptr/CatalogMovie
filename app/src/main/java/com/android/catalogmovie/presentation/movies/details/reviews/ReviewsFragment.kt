package com.android.catalogmovie.presentation.movies.details.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.databinding.FragmentReviewBinding
import com.android.catalogmovie.presentation.movies.details.MovieDetailsViewModel
import com.android.catalogmovie.presentation.movies.details.PagerAdapter
import com.paem.core.entities.Review
import com.paem.core.utils.gone
import com.paem.core.utils.show
import com.paem.core.utils.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewsFragment : Fragment() {

    private val vm: MovieDetailsViewModel by viewModel()

    private val reviewsAdapter = ReviewListPagingAdapter()

    private val binding by lazy { FragmentReviewBinding.inflate(layoutInflater) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt(PagerAdapter.KEY_ID) ?: 0
        lifecycleScope.launch {
            vm.getReviews(movieId).observe(requireActivity()) {
                showReviews(it)
            }
        }
    }

    private fun showReviews(reviewList: PagingData<Review>) {
        with(binding) {
            reviewsAdapter.submitData(lifecycle, reviewList)
            rvReviews.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = reviewsAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        }
        setupLoadingState()
    }

    private fun setupLoadingState() {
        with(binding) {
            reviewsAdapter.addLoadStateListener { loadState ->
                // show empty list
                if (loadState.refresh is LoadState.Loading ||
                    loadState.append is LoadState.Loading
                )
                    progressBar.show()
                else {
                    progressBar.gone()
                    // If we have an error, show a toast
                    val errorState = when {
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    errorState?.let {
                        println("ERROR: ${it.error.toString()}")
                        activity?.showToast(it.error.toString())
                    }

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

}