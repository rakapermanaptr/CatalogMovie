package com.android.catalogmovie.presentation.movies.details.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.databinding.ItemVideoBinding
import com.paem.core.entities.Video

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    private val itemList = mutableListOf<Video>()

    fun addItems(itemList: List<Video>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemVideoBinding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemVideoBinding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList[position]
        holder.bind(data)
    }

    inner class ViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Video) {
            with(binding) {
                wvVideo.loadUrl(data.url)
                wvVideo.settings.javaScriptEnabled = true
            }
        }
    }

}