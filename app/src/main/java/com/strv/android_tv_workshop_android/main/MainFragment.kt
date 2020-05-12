package com.strv.android_tv_workshop_android.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.strv.android_tv_workshop_android.AndroidTVApp
import com.strv.android_tv_workshop_android.R
import com.strv.android_tv_workshop_android.domain.Movie
import com.strv.android_tv_workshop_android.glide.GlideBackgroundManager
import com.strv.android_tv_workshop_android.player.MoviePresenter
import com.strv.android_tv_workshop_android.player.PlayerActivity
import com.strv.android_tv_workshop_android.storage.BACKDROP_URL
import com.strv.android_tv_workshop_android.storage.Storage
import javax.inject.Inject

const val EXTRA_MOVIE = "extra_movie"

class MainFragment : BrowseSupportFragment(), OnItemViewSelectedListener,
	OnItemViewClickedListener {
	@Inject
	lateinit var storage: Storage

	private val backgroundManager by lazy { GlideBackgroundManager(requireActivity()) }

	override fun onAttach(context: Context?) {
		super.onAttach(context)

		(requireActivity().application as AndroidTVApp).appComponent.inject(this)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		setupUi()
		loadRows()
	}

	override fun onItemSelected(
		itemViewHolder: Presenter.ViewHolder?,
		item: Any?,
		rowViewHolder: RowPresenter.ViewHolder?,
		row: Row?
	) {
		when (item) {
			is Movie -> {
				backgroundManager.loadImage(BACKDROP_URL + item.backdrop)
			}
		}
	}

	override fun onItemClicked(
		itemViewHolder: Presenter.ViewHolder?,
		item: Any?,
		rowViewHolder: RowPresenter.ViewHolder?,
		row: Row?
	) {
		when (item) {
			is Movie -> {
				val playerIntent = Intent(requireActivity(), PlayerActivity::class.java).apply {
					putExtra(EXTRA_MOVIE, item)
				}
				startActivity(playerIntent)
			}
		}
	}

	private fun loadRows() {
		with(requireActivity()) {
			val rowsAdapter = ArrayObjectAdapter(
				ListRowPresenter()
			)

			storage.moviesState.subscribe({ state ->
				addMovies(
					state.nowPlayingMovies,
					rowsAdapter,
					getString(R.string.header_now_playing)
				)
				addMovies(state.upcomingMovies, rowsAdapter, getString(R.string.header_upcoming))
				addMovies(state.popularMovies, rowsAdapter, getString(R.string.header_popular))
				addMovies(state.topRatedMovies, rowsAdapter, getString(R.string.header_top_rated))

			}, Throwable::printStackTrace)

			adapter = rowsAdapter
		}
	}

	private fun setupUi() {
		headersState = HEADERS_ENABLED
		isHeadersTransitionOnBackEnabled = true
		prepareEntranceTransition()
		onItemViewSelectedListener = this
		onItemViewClickedListener = this
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