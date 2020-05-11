package com.strv.android_tv_workshop_android.player

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.strv.android_tv_workshop_android.R
import com.strv.android_tv_workshop_android.domain.Movie
import com.strv.android_tv_workshop_android.storage.POSTER_URL
import com.strv.android_tv_workshop_android.widget.BindableCardView

class MovieCardView(context: Context) : BindableCardView<Movie>(context) {
	private val title = findViewById<TextView>(R.id.title)
	private val image = findViewById<ImageView>(R.id.image)

	override fun bind(movie: Movie) {
		title.text = movie.title

		Glide.with(context)
			.load(POSTER_URL + movie.poster)
			.into(image)
	}

	override fun getLayoutResource(): Int {
		return R.layout.card_movie
	}
}