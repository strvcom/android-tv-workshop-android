package com.strv.android_tv_workshop_android.domain

data class Song(
	val title: String,
	val description: String,
	val text: String,
	val image: String,
	val file: String,
	val duration: String,
	val number: Int,
	var favorite: Boolean = false
)