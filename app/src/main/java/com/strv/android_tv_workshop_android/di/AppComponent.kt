package com.strv.android_tv_workshop_android.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
	@Component.Factory
	interface Factory {
		fun create(@BindsInstance applicationContext: Context): AppComponent
	}
}