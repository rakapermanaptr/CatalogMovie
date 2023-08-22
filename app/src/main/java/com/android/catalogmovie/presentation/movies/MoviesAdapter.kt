package com.android.catalogmovie.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.BuildConfig
import com.android.catalogmovie.data.remote.model.Movie
import com.android.catalogmovie.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MoviesAdapter(private val onItemClick: (item: Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val itemList = mutableListOf<Movie>()

    fun addItems(itemList: List<Movie>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMovieBinding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClick(data) }
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) {
            with(binding) {
                Glide.with(binding.root)
                    .load("${BuildConfig.BASE_URL_IMAGE}${data.posterPath}")
                    .into(imgPoster)
                tvTitle.text = data.originalTitle
            }
        }
    }

}