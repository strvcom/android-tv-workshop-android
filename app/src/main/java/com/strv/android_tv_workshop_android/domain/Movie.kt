package com.strv.android_tv_workshop_android.domain

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
	val poster: String,
	val backdrop: String,
	val title: String,
	val trailer: String
) : Parcelable