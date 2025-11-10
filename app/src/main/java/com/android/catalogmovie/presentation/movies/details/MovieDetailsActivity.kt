package com.android.catalogmovie.presentation.movies.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.catalogmovie.R
import com.android.catalogmovie.databinding.ActivityMovieDetailsBinding
import com.paem.core.utils.gone
import com.paem.core.utils.handleErrorState
import com.paem.core.utils.show
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.paem.core.data.remote.RequestState
import com.paem.core.entities.MovieDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private val vm: MovieDetailsViewModel by viewModel()

    private val binding by lazy { ActivityMovieDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(R.style.OptOutEdgeToEdgeEnforcement,false)
        setContentView(binding.root)
        with(binding) {
            val movieId = intent.getIntExtra(KEY_MOVIE_ID, 0)
            initView(movieId = movieId)

            vm.getMovieDetails(movieId) { state ->
                when (state) {
                    RequestState.Loading -> progressBar.show()
                    is RequestState.Success -> {
                        progressBar.gone()
                        showMovieDetail(state.result)
                    }
                    is RequestState.Failed -> {
                        progressBar.show()
                        handleErrorState(state.error)
                    }
                }
            }
        }
    }

    private fun initView(movieId: Int) {
        setupToolbar()
        setupViewPager(movieId)
    }

    private fun showMovieDetail(movie: MovieDetail) {
        with(binding) {
            toolbar.title = movie.title
            Glide.with(binding.root)
                .load(movie.imageUrl)
                .into(imgBackdrop)
            tvName.text = movie.title
            tvRating.text = movie.rating
        }
    }

    private fun setupViewPager(movieId: Int) {
        with(binding) {
            val pagerAdapter = PagerAdapter(this@MovieDetailsActivity, movieId)
            vpLanding.adapter = pagerAdapter
            TabLayoutMediator(tlLanding, vpLanding) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setTitleTextColor(getColor(R.color.white))
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private val tabTitles = arrayOf("Reviews", "Videos")
    companion object {
        const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
    }
}