package com.android.catalogmovie.presentation.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.data.remote.model.Genres
import com.android.catalogmovie.databinding.ItemGenreBinding

class GenresAdapter(private val onItemClick: (item: Genres.Genre) -> Unit) :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private val itemList = mutableListOf<Genres.Genre>()

    fun addItems(itemList: List<Genres.Genre>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemGenreBinding =
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemGenreBinding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClick(data) }
    }

    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Genres.Genre) {
            with(binding) {
                tvGenre.text = data.name
            }
        }
    }

}