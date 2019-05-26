package com.alphan.mainactivity.ui.mainActivity

import com.arellomobile.mvp.MvpView

interface MainActivityView : MvpView {

    fun setGeoStatus(hasPermission: Boolean)
    fun setRadiusSwitcherStatus(shouldShowRadius: Boolean)
}