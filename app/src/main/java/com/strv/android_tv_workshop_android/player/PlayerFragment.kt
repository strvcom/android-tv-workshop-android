package com.strv.android_tv_workshop_android.player

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackTransportControlGlue
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.strv.android_tv_workshop_android.domain.Movie
import com.strv.android_tv_workshop_android.main.EXTRA_MOVIE

class PlayerFragment : VideoSupportFragment() {
	private val player by lazy { SimpleExoPlayer.Builder(requireActivity()).build() }
	private val playerGlue by lazy {
		PlaybackTransportControlGlue(
			activity,
			LeanbackPlayerAdapter(requireActivity(), player, 1000)
		)
	}

	override fun onStart() {
		super.onStart()

		initializePlayer(requireActivity().intent.getParcelableExtra(EXTRA_MOVIE) as Movie)
	}

	override fun onStop() {
		super.onStop()

		releasePlayer()
	}

	private fun initializePlayer(movie: Movie) {
		playerGlue.host = VideoSupportFragmentGlueHost(this)
		fetchSongMetadata(movie)
		val mediaSource = buildMediaSource(Uri.parse(movie.trailer))
		player.prepare(mediaSource!!)

		playerGlue.playWhenPrepared()
	}

	private fun fetchSongMetadata(movie: Movie) {
		val metadataRetriever = MediaMetadataRetriever()
		metadataRetriever.setDataSource(movie.trailer, mutableMapOf())
		playerGlue.title =
			metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
		playerGlue.subtitle =
			metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
	}

	private fun buildMediaSource(uri: Uri): MediaSource? {
		val dataSourceFactory: DataSource.Factory =
			DefaultDataSourceFactory(requireActivity(), "activevideo-benchmark")
		return ProgressiveMediaSource.Factory(dataSourceFactory)
			.createMediaSource(uri)
	}

	private fun releasePlayer() {
		player.release()
	}
}