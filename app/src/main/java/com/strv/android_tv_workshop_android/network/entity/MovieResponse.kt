package com.strv.android_tv_workshop_android.network.entity

import com.strv.android_tv_workshop_android.domain.Movie

data class MovieResponse(
	val movies: List<Movie>
)