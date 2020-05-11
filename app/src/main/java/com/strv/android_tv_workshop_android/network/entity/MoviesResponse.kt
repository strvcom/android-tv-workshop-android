package com.strv.android_tv_workshop_android.network.entity

import com.squareup.moshi.JsonClass
import com.strv.android_tv_workshop_android.domain.Movie

@JsonClass(generateAdapter = true)
data class MoviesResponse(
	val movies: List<Movie>
)