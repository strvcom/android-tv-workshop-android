package com.strv.android_tv_workshop_android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.leanback.widget.BaseCardView

abstract class BindableCardView<T> : BaseCardView {
	constructor(context: Context) : this(context, null)
	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
		super(context, attrs, defStyleAttr) {
		initLayout()
	}

	private fun initLayout() {
		isFocusable = true
		isFocusableInTouchMode = true
		val inflater = LayoutInflater.from(context)
		inflater.inflate(getLayoutResource(), this)
	}

	abstract fun bind(data: T)
	@LayoutRes
	protected abstract fun getLayoutResource(): Int
}