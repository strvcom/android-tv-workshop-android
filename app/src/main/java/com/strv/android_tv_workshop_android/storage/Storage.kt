package com.strv.android_tv_workshop_android.storage

import android.content.Context
import androidx.annotation.RawRes
import com.squareup.moshi.Moshi
import com.strv.android_tv_workshop_android.R
import com.strv.android_tv_workshop_android.domain.Movie
import com.strv.android_tv_workshop_android.domain.Song
import com.strv.android_tv_workshop_android.extensions.update
import com.strv.android_tv_workshop_android.network.entity.MoviesResponse
import com.strv.android_tv_workshop_android.network.entity.SongsResponse
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

const val POSTER_URL = "http://image.tmdb.org/t/p/w500"
const val BACKDROP_URL = "http://image.tmdb.org/t/p/w1280"

class Storage @Inject constructor(val context: Context) {
	val moviesState: BehaviorSubject<MoviesState> = BehaviorSubject.createDefault(
		MoviesState(emptyList(), emptyList(), emptyList(), emptyList())
	)
	val songsState: BehaviorSubject<SongsState> = BehaviorSubject.createDefault(
		SongsState(emptyList())
	)
	private val moviesResponseAdapter = Moshi.Builder().build().adapter(MoviesResponse::class.java)
	private val songsResponseAdapter = Moshi.Builder().build().adapter(SongsResponse::class.java)

	init {
		fetchNowPlayingMovies()
		fetchTopRatedMovies()
		fetchUpcomingMovies()
		fetchPopularMovies()
		
		fetchSongs()
	}

	private fun fetchNowPlayingMovies() {
		moviesState.update { copy(nowPlayingMovies = getMoviesFromRaw(R.raw.now_playing)) }
	}

	private fun fetchTopRatedMovies() {
		moviesState.update { copy(topRatedMovies = getMoviesFromRaw(R.raw.top_rated)) }
	}

	private fun fetchPopularMovies() {
		moviesState.update { copy(popularMovies = getMoviesFromRaw(R.raw.popular)) }
	}

	private fun fetchUpcomingMovies() {
		moviesState.update { copy(upcomingMovies = getMoviesFromRaw(R.raw.upcoming)) }
	}

	private fun fetchSongs() {
		songsState.update { copy(songs = getSongsFromRaw(R.raw.songs)) }
	}

	private fun getMoviesFromRaw(@RawRes resourceId: Int): List<Movie> {
		val json = context.resources.openRawResource(resourceId).bufferedReader().readText()

		return moviesResponseAdapter.fromJson(json)?.movies ?: emptyList()
	}

	private fun getSongsFromRaw(@RawRes resourceId: Int): List<Song> {
		val json = context.resources.openRawResource(resourceId).bufferedReader().readText()

		return songsResponseAdapter.fromJson(json)?.songs ?: emptyList()
	}

}

data class MoviesState(
	val nowPlayingMovies: List<Movie>,
	val topRatedMovies: List<Movie>,
	val upcomingMovies: List<Movie>,
	val popularMovies: List<Movie>
)

data class SongsState(
	val songs: List<Song>
)