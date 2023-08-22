package com.android.catalogmovie.utils

import android.app.Activity
import android.content.Intent
import com.android.catalogmovie.presentation.movies.MoviesActivity
import com.android.catalogmovie.presentation.movies.details.MovieDetailsActivity

object NavigationUtils {

    fun navigateToMovies(activity: Activity, genreId: Int?) {
        val intent = Intent(activity, MoviesActivity::class.java)
        intent.putExtra(MoviesActivity.KEY_GENRE_ID, genreId)
        activity.startActivity(intent)
    }

    fun navigateToMovieDetails(activity: Activity, movieId: Int?) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.KEY_MOVIE_ID, movieId)
        activity.startActivity(intent)
    }
}