package com.android.catalogmovie.presentation.movies.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.catalogmovie.R
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.databinding.ActivityMovieDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private val vm: MovieDetailsViewModel by viewModel()

    private val binding by lazy { ActivityMovieDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val movieId = intent.getIntExtra(KEY_MOVIE_ID, 0)
        initView(movieId = movieId)

        println("movieId: $movieId")
        vm.getMovieDetails(movieId) { state ->
            when (state) {
                is RequestState.Failed -> println("Failed")
                RequestState.Loading -> println("Loading")
                is RequestState.Success -> println("Success, data: ${state.result}")
            }
        }
    }

    private fun initView(movieId: Int) {
        setupToolbar()
        setupViewPager(movieId)
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
            toolbar.title = "Test"
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