package com.android.catalogmovie.presentation.movies.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        return ReviewsFragment()
    }
}