package com.strv.android_tv_workshop_android.network.entity

import com.strv.android_tv_workshop_android.domain.Song

data class SongsResponse(
	val songs: List<Song>
)