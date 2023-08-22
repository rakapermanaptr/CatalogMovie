package com.android.catalogmovie.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.BuildConfig
import com.android.catalogmovie.data.remote.model.Movie
import com.android.catalogmovie.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MoviesPagingAdapter(private val onItemClick: (item: Movie?) -> Unit) :
    PagedListAdapter<Movie, MoviesPagingAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClick(getItem(position)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMovieBinding)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie?) {
            with(binding) {
                data?.let {
                    Glide.with(binding.root)
                        .load("${BuildConfig.BASE_URL_IMAGE}${data.posterPath}")
                        .into(imgPoster)
                    tvTitle.text = data.originalTitle
                }
            }
        }
    }

}