package com.android.catalogmovie.presentation.genres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.android.catalogmovie.R
import com.android.catalogmovie.databinding.ActivityGenresBinding
import com.android.catalogmovie.utils.NavigationUtils
import com.paem.core.data.remote.RequestState
import com.paem.core.entities.Genre
import com.paem.core.utils.gone
import com.paem.core.utils.handleErrorState
import com.paem.core.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresActivity : AppCompatActivity() {

    private val genreAdapter = GenresAdapter {
        NavigationUtils.navigateToMovies(this, it.id)
    }

    private val vm: GenresViewModel by viewModel()

    private val binding by lazy { ActivityGenresBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(R.style.OptOutEdgeToEdgeEnforcement,false)
        setContentView(binding.root)

        observeViewModel()
    }

    private fun observeViewModel() = with(binding) {
        vm.getGenres { state ->
            when (state) {
                RequestState.Loading -> progressBar.show()
                is RequestState.Success -> {
                    progressBar.gone()
                    showGenresList(state.result)
                }
                is RequestState.Failed -> {
                    progressBar.gone()
                    handleErrorState(state.error)
                }
            }
        }
    }

    private fun showGenresList(genreList: List<Genre>) {
        with(binding) {
            genreAdapter.addItems(genreList)
            rvGenres.apply {
                layoutManager = GridLayoutManager(this@GenresActivity, 2)
                adapter = genreAdapter
            }
        }
    }
}