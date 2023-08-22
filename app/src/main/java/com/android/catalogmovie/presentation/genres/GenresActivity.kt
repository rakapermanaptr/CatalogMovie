package com.android.catalogmovie.presentation.genres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.data.remote.model.Genres
import com.android.catalogmovie.databinding.ActivityGenresBinding
import com.android.catalogmovie.utils.gone
import com.android.catalogmovie.utils.handleErrorState
import com.android.catalogmovie.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresActivity : AppCompatActivity() {

    private val genreAdapter = GenresAdapter {}

    private val vm: GenresViewModel by viewModel()

    private val binding by lazy { ActivityGenresBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeViewModel()
    }

    private fun observeViewModel() = with(binding) {
        vm.getGenres { state ->
            when (state) {
                RequestState.Loading -> progressBar.show()
                is RequestState.Success -> {
                    progressBar.gone()
                    showGenresList(state.result.genres ?: emptyList())
                }
                is RequestState.Failed -> {
                    progressBar.gone()
                    handleErrorState(state.error)
                }
            }
        }
    }

    private fun showGenresList(genreList: List<Genres.Genre>) {
        with(binding) {
            genreAdapter.addItems(genreList)
            rvGenres.apply {
                layoutManager = GridLayoutManager(this@GenresActivity, 2)
                adapter = genreAdapter
            }
        }
    }
}