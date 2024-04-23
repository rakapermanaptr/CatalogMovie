package com.android.catalogmovie.presentation.movies.details.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.databinding.ItemReviewBinding
import com.paem.core.entities.Review

class ReviewListPagingAdapter :
    PagingDataAdapter<Review, ReviewListPagingAdapter.ViewHolder>(ReviewComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)!!
        holder.bind(review)
        holder.itemView.setOnClickListener {
            if (review.isReadMore) {
                review.isReadMore = false
                holder.binding.tvReview.maxLines = 3
            } else {
                review.isReadMore = true
                holder.binding.tvReview.maxLines = review.review.length
            }
        }
    }

    class ViewHolder( val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Review) {
            with(binding) {
                tvReview.text = data.review
                tvAuthor.text = data.author
            }
        }
    }

    object ReviewComparator : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }
    }
}