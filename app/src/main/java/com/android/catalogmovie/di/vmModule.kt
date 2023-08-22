package com.android.catalogmovie.di

import com.android.catalogmovie.presentation.genres.GenresViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { GenresViewModel() }
}