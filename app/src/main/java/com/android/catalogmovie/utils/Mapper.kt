package com.android.catalogmovie.utils

import com.android.catalogmovie.BuildConfig
import com.android.catalogmovie.data.remote.model.*
import com.android.catalogmovie.domain.entities.*

fun Genres.Genre.toGenre(): Genre {
    return Genre(id = id, name = name)
}

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        imageUrl = BuildConfig.BASE_URL_IMAGE + posterPath,
        title = title
    )
}

fun MovieDetailsResponse.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        imageUrl = BuildConfig.BASE_URL_IMAGE + backdropPath,
        title = originalTitle,
        rating = "Rating: ${voteAverage?.round()}"
    )
}

fun ReviewsResponse.Review.toReview(): Review {
    return Review(
        id = id,
        review = content.orEmpty(),
        author = "Author: $author"
    )
}

fun VideosResponse.Video.toVideo(): Video {
    return Video(url = getVideoUrl())
}