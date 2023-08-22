package com.android.catalogmovie.presentation.movies.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.catalogmovie.presentation.movies.details.reviews.ReviewsFragment
import com.android.catalogmovie.presentation.movies.details.videos.VideosFragment

class PagerAdapter(activity: FragmentActivity, private val movieId: Int) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        val arg = Bundle()
        arg.putInt("KEY_ID", movieId)
        return if (position == 0) {
            val reviewsFragment = ReviewsFragment().apply {
                arguments = arg
            }
            reviewsFragment
        } else {
            val videosFragment = VideosFragment().apply {
                arguments = arg
            }
            videosFragment
        }
    }
}