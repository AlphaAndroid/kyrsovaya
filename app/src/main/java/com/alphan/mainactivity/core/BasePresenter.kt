package com.alphan.mainactivity.core

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

open class BasePresenter<View : MvpView> : MvpPresenter<View>() {
}