package com.strv.android_tv_workshop_android.player

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.strv.android_tv_workshop_android.R

class PlayerActivity : FragmentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setContentView(R.layout.activity_player)
	}
}