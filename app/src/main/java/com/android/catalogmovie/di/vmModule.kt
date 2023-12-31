package com.android.catalogmovie.di

import com.android.catalogmovie.presentation.genres.GenresViewModel
import com.android.catalogmovie.presentation.movies.MoviesViewModel
import com.android.catalogmovie.presentation.movies.details.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { GenresViewModel() }
    viewModel { MoviesViewModel() }
    viewModel { MovieDetailsViewModel() }
}