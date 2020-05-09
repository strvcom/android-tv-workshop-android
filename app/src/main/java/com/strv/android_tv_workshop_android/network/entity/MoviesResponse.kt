package com.strv.android_tv_workshop_android.network.entity

import com.strv.android_tv_workshop_android.domain.Movie

data class MoviesResponse(
	val movies: List<Movie>
)