package com.strv.android_tv_workshop_android.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie (
	val poster: String,
	val backdrop: String,
	val title: String,
	val trailer: String
)