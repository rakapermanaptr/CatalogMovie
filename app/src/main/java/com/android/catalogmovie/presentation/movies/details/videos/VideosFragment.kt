package com.android.catalogmovie.presentation.movies.details.videos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.catalogmovie.data.remote.RequestState
import com.android.catalogmovie.databinding.FragmentVideosBinding
import com.android.catalogmovie.domain.entities.Video
import com.android.catalogmovie.presentation.movies.details.MovieDetailsViewModel
import com.android.catalogmovie.presentation.movies.details.PagerAdapter
import com.android.catalogmovie.utils.gone
import com.android.catalogmovie.utils.handleErrorState
import com.android.catalogmovie.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideosFragment : Fragment() {

    private val vm: MovieDetailsViewModel by viewModel()

    private val binding by lazy { FragmentVideosBinding.inflate(layoutInflater) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val movieId = arguments?.getInt(PagerAdapter.KEY_ID) ?: 0
            vm.getVideos(movieId) { state ->
                when (state) {
                    RequestState.Loading -> progressBar.show()
                    is RequestState.Success -> {
                        progressBar.gone()
                        showVideos(state.result)
                    }
                    is RequestState.Failed -> {
                        progressBar.gone()
                        activity?.handleErrorState(state.error)
                    }
                }
            }
        }
    }

    private fun showVideos(videoList: List<Video>) {
        with(binding) {
            val videosAdapter = VideosAdapter()
            videosAdapter.addItems(videoList)
            rvVideos.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = videosAdapter
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

}