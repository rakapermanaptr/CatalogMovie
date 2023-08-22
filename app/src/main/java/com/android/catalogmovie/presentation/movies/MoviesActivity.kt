package com.android.catalogmovie.presentation.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.data.remote.model.Movie
import com.android.catalogmovie.databinding.ActivityMoviesBinding
import com.android.catalogmovie.utils.gone
import com.android.catalogmovie.utils.handleErrorState
import com.android.catalogmovie.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity() {

    private val vm: MoviesViewModel by viewModel()

    private val moviesAdapter = MoviesAdapter {

    }

    private val binding by lazy { ActivityMoviesBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val genreId = intent.getIntExtra(KEY_GENRE_ID, 0)
        observeViewModel(genreId = genreId)
    }

    private fun observeViewModel(genreId: Int?) = with(binding) {
        vm.getMovies(genreId) {state ->
            when (state) {
                RequestState.Loading -> progressBar.show()
                is RequestState.Success -> {
                    progressBar.gone()
                    showMovies(state.result)
                }
                is RequestState.Failed -> {
                    progressBar.gone()
                    handleErrorState(state.error)
                }
            }
        }
    }

    private fun showMovies(movieList: List<Movie>?) {
        with(binding) {
            movieList?.let {
                moviesAdapter.addItems(it)
                rvMovies.apply {
                    layoutManager = GridLayoutManager(this@MoviesActivity, 2)
                    adapter = moviesAdapter
                }
            }
        }
    }

    companion object {
        const val KEY_GENRE_ID = "KEY_GENRE_ID"
    }
}