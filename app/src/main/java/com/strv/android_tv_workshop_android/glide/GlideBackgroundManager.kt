package com.strv.android_tv_workshop_android.glide

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import androidx.leanback.app.BackgroundManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.lang.ref.WeakReference
import java.util.*

private const val BACKGROUND_UPDATE_DELAY = 200L

class GlideBackgroundManager(activity: Activity) {
	private val activityWeakReference: WeakReference<Activity> = WeakReference(activity)
	private val backgroundManager: BackgroundManager = BackgroundManager.getInstance(activity)
	private val handler: Handler = Handler(Looper.getMainLooper())
	private lateinit var backgroundUri: String
	private lateinit var backgroundTimer: Timer

	private val glideTarget: CustomTarget<Drawable> = object : CustomTarget<Drawable>() {
		override fun onLoadCleared(placeholder: Drawable?) {

		}

		override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
			setBackground(resource)
		}
	}

	init {
		backgroundManager.attach(activity.window)
	}

	fun loadImage(imageUrl: String) {
		backgroundUri = imageUrl
		startBackgroundTimer()
	}

	fun setBackground(drawable: Drawable?) {
		if (!backgroundManager.isAttached) {
			backgroundManager.attach(activityWeakReference.get()?.window)
		}
		backgroundManager.drawable = drawable
	}

	private fun cancelTimer() {
		if (::backgroundTimer.isInitialized) {
			backgroundTimer.cancel()
		}
	}

	private fun startBackgroundTimer() {
		cancelTimer()
		backgroundTimer = Timer()
		backgroundTimer.schedule(
			object : TimerTask() {
				override fun run() {
					handler.post {
						updateBackground()
					}
				}
			},
			BACKGROUND_UPDATE_DELAY
		)
	}

	fun updateBackground() {
		if (activityWeakReference.get() != null) {
			Glide.with(activityWeakReference.get() as Activity)
				.load(backgroundUri)
				.into(glideTarget)
		}
	}
}