package com.android.catalogmovie.presentation.movies.details.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.data.remote.model.ReviewsResponse
import com.android.catalogmovie.databinding.ItemReviewBinding

class ReviewListPagingAdapter :
    PagingDataAdapter<ReviewsResponse.Review, ReviewListPagingAdapter.ViewHolder>(ReviewComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)!!
        holder.bind(review)
    }

    class ViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewsResponse.Review) {
            with(binding) {
                tvReview.text = data.content
                tvAuthor.text = "Author: ${data.author}"
            }
        }
    }

    object ReviewComparator : DiffUtil.ItemCallback<ReviewsResponse.Review>() {
        override fun areItemsTheSame(
            oldItem: ReviewsResponse.Review,
            newItem: ReviewsResponse.Review
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReviewsResponse.Review,
            newItem: ReviewsResponse.Review
        ): Boolean {
            return oldItem == newItem
        }
    }
}