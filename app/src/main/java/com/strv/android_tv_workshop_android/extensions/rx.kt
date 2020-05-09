package com.strv.android_tv_workshop_android.extensions

import io.reactivex.rxjava3.subjects.BehaviorSubject

fun <T> BehaviorSubject<T>.update(update: T.() -> T) = onNext(value!!.update())