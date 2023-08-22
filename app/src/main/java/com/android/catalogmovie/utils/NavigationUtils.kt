package com.android.catalogmovie.utils

import android.app.Activity
import android.content.Intent
import com.android.catalogmovie.presentation.movies.MoviesActivity

object NavigationUtils {

    fun navigateToMovies(activity: Activity, genreId: Int?) {
        val intent = Intent(activity, MoviesActivity::class.java)
        intent.putExtra(MoviesActivity.KEY_GENRE_ID, genreId)
        activity.startActivity(intent)
    }
}