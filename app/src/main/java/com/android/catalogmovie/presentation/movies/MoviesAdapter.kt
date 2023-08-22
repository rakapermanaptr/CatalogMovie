package com.android.catalogmovie.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.databinding.ItemMovieBinding
import com.android.catalogmovie.domain.entities.Movie
import com.bumptech.glide.Glide

class MovieListPagingAdapter(private val onItemClick: (item: Movie) -> Unit) :
    PagingDataAdapter<Movie, MovieListPagingAdapter.ViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.binding.root.setOnClickListener { onItemClick(movie) }
        holder.bind(movie)
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(binding.root)
                    .load(data.imageUrl)
                    .into(imgPoster)
                tvTitle.text = data.title
            }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}