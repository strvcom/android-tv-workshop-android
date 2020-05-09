package com.strv.android_tv_workshop_android.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import com.strv.android_tv_workshop_android.AndroidTVApp
import com.strv.android_tv_workshop_android.storage.Storage
import javax.inject.Inject

class MainFragment : BrowseSupportFragment() {
	@Inject
	lateinit var storage: Storage

	override fun onAttach(context: Context?) {
		super.onAttach(context)

		(activity?.application as AndroidTVApp).appComponent.inject(this)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

	}
}