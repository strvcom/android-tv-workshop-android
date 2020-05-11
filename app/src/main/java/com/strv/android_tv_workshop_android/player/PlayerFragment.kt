package com.strv.android_tv_workshop_android.player

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.leanback.app.PlaybackSupportFragment
import androidx.leanback.app.PlaybackSupportFragmentGlueHost
import androidx.leanback.media.PlaybackTransportControlGlue
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

const val SONG_URL = "https://drive.google.com/uc?id=1hGkfMQv9n7-gBq7BepXe33ZmPZT8cmCu"

class PlayerFragment : PlaybackSupportFragment() {
	private lateinit var player: SimpleExoPlayer
	private lateinit var playerGlue: PlaybackTransportControlGlue<LeanbackPlayerAdapter>

	override fun onStart() {
		super.onStart()

		initializePlayer()
	}

	override fun onStop() {
		super.onStop()

		releasePlayer()
	}

	private fun initializePlayer() {
		player = SimpleExoPlayer.Builder(activity!!).build()
		val adapter = LeanbackPlayerAdapter(activity!!, player, 1000)

		playerGlue = PlaybackTransportControlGlue(activity, adapter)
		playerGlue.host = PlaybackSupportFragmentGlueHost(this)
		fetchSongMetadata()
		val mediaSource = buildMediaSource(Uri.parse(SONG_URL))
		player.prepare(mediaSource!!)

		playerGlue.playWhenPrepared()
	}

	private fun fetchSongMetadata() {
		val metadataRetriever = MediaMetadataRetriever()
		metadataRetriever.setDataSource(SONG_URL, mutableMapOf())
		playerGlue.title =
			metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
		playerGlue.subtitle =
			metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
	}

	private fun buildMediaSource(uri: Uri): MediaSource? {
		val dataSourceFactory: DataSource.Factory =
			DefaultDataSourceFactory(activity!!, "activevideo-benchmark")
		return ProgressiveMediaSource.Factory(dataSourceFactory)
			.createMediaSource(uri)
	}

	private fun releasePlayer() {
		if (::player.isInitialized) {
			player.release()
		}
	}
}