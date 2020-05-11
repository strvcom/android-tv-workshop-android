package com.strv.android_tv_workshop_android.main

import android.content.Context
import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.strv.android_tv_workshop_android.AndroidTVApp
import com.strv.android_tv_workshop_android.R
import com.strv.android_tv_workshop_android.domain.Movie
import com.strv.android_tv_workshop_android.player.MoviePresenter
import com.strv.android_tv_workshop_android.storage.Storage
import javax.inject.Inject

class MainFragment : BrowseSupportFragment() {
	@Inject
	lateinit var storage: Storage

	override fun onAttach(context: Context?) {
		super.onAttach(context)

		(activity!!.application as AndroidTVApp).appComponent.inject(this)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		setupUi()
		loadRows()
	}

	private fun loadRows() {
		activity?.let {
			val rowsAdapter = ArrayObjectAdapter(
				ListRowPresenter()
			)

			storage.moviesState.subscribe({
				addMovies(it.nowPlayingMovies, rowsAdapter, getString(R.string.header_now_playing))
				addMovies(it.upcomingMovies, rowsAdapter, getString(R.string.header_upcoming))
				addMovies(it.popularMovies, rowsAdapter, getString(R.string.header_popular))
				addMovies(it.topRatedMovies, rowsAdapter, getString(R.string.header_top_rated))
				
			}, {})

			adapter = rowsAdapter
		}
	}

	private fun setupUi() {
		headersState = HEADERS_ENABLED
		isHeadersTransitionOnBackEnabled = true
		prepareEntranceTransition()
	}

	private fun addMovies(
		movies: List<Movie>,
		rowsAdapter: ArrayObjectAdapter,
		header: String
	) {
		val listRowAdapter = ArrayObjectAdapter(MoviePresenter())
		movies.forEach { listRowAdapter.add(it) }
		val moviesHeader = HeaderItem(-1, header)
		rowsAdapter.add(ListRow(moviesHeader, listRowAdapter))
	}
}