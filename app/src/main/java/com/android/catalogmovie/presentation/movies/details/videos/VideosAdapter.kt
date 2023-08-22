package com.android.catalogmovie.presentation.movies.details.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.data.remote.model.VideosResponse
import com.android.catalogmovie.databinding.ItemVideoBinding

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    private val itemList = mutableListOf<VideosResponse.Video>()

    fun addItems(itemList: List<VideosResponse.Video>) {
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
        fun bind(data: VideosResponse.Video) {
            with(binding) {
                wvVideo.loadUrl(data.getVideoUrl())
                wvVideo.settings.javaScriptEnabled = true
            }
        }
    }

}