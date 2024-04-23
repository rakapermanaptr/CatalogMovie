package com.android.catalogmovie.presentation.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.databinding.ItemGenreBinding
import com.paem.core.entities.Genre

class GenresAdapter(private val onItemClick: (item: Genre) -> Unit) :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private val itemList = mutableListOf<Genre>()

    fun addItems(itemList: List<Genre>) {
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

        fun bind(data: Genre) {
            with(binding) {
                tvGenre.text = data.name
            }
        }
    }

}