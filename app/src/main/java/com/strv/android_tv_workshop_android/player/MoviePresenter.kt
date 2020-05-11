package com.strv.android_tv_workshop_android.player

import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import com.strv.android_tv_workshop_android.domain.Movie

class MoviePresenter : Presenter() {
	override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
		return ViewHolder(MovieCardView(parent.context))
	}

	override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
		(viewHolder.view as MovieCardView).bind(item as Movie)
	}

	override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
	}

}