package com.strv.android_tv_workshop_android

import android.app.Application
import com.strv.android_tv_workshop_android.di.AppComponent
import com.strv.android_tv_workshop_android.di.DaggerAppComponent

open class AndroidTVApp : Application(){
	val appComponent: AppComponent by lazy {
		DaggerAppComponent.factory().create(this)
	}
}