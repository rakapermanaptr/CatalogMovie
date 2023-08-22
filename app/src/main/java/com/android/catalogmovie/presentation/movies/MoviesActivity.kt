package com.android.catalogmovie.presentation.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.databinding.ActivityMoviesBinding

class MoviesActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMoviesBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?): Unit = with(binding) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val genreId = intent.getIntExtra(KEY_GENRE_ID, 0)
        val vm = MoviesViewModel(genreId)

        val adapter = MoviesPagingAdapter {}
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            fun onChange() {
                val count = adapter.itemCount
                vm.setItemSize(count)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                onChange()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                onChange()
            }
        })
        rvMovies.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(this@MoviesActivity, 2)
        }
        vm.pagingList.observe(this@MoviesActivity) {
            adapter.submitList(it)
        }
        lifecycleScope.launchWhenCreated {
            vm.renderState.collect {
                if (it is MoviesViewModel.RenderState.Loading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        const val KEY_GENRE_ID = "KEY_GENRE_ID"
    }
}