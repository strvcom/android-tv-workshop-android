package com.strv.android_tv_workshop_android.player

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.PlaybackSupportFragment
import androidx.leanback.app.PlaybackSupportFragmentGlueHost
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
	private lateinit var player: SimpleExoPlayer
	private lateinit var playerGlue: PlaybackTransportControlGlue<LeanbackPlayerAdapter>
	private lateinit var movie: Movie

	override fun onStart() {
		super.onStart()

		initializePlayer()
	}

	override fun onStop() {
		super.onStop()

		releasePlayer()
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		movie = activity!!.intent.getParcelableExtra(EXTRA_MOVIE) as Movie
	}

	private fun initializePlayer() {
		player = SimpleExoPlayer.Builder(activity!!).build()
		val adapter = LeanbackPlayerAdapter(activity!!, player, 1000)

		playerGlue = PlaybackTransportControlGlue(activity, adapter)
		playerGlue.host = VideoSupportFragmentGlueHost(this)
		fetchSongMetadata()
		val mediaSource = buildMediaSource(Uri.parse(movie.trailer))
		player.prepare(mediaSource!!)

		playerGlue.playWhenPrepared()
	}

	private fun fetchSongMetadata() {
		val metadataRetriever = MediaMetadataRetriever()
		metadataRetriever.setDataSource(movie.trailer, mutableMapOf())
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