package com.android.catalogmovie.presentation.movies.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.catalogmovie.R
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.databinding.ActivityMovieDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private val vm: MovieDetailsViewModel by viewModel()

    private val binding by lazy { ActivityMovieDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.title = "Test"
        binding.toolbar.setTitleTextColor(getColor(R.color.white))
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val movieId = intent.getIntExtra(KEY_MOVIE_ID, 0)
        println("movieId: $movieId")
        vm.getMovieDetails(movieId) { state ->
            when (state) {
                is RequestState.Failed -> println("Failed")
                RequestState.Loading -> println("Loading")
                is RequestState.Success -> println("Success, data: ${state.result}")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
    }
}