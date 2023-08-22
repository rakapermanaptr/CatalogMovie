package com.android.catalogmovie.presentation.movies.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.catalogmovie.R
import com.android.catalogmovie.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMovieDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(KEY_MOVIE_ID, 0)
        println("movieId: $movieId")
    }

    companion object {
        const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
    }
}