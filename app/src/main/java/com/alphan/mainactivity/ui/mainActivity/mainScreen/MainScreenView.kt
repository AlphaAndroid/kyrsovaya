package com.alphan.mainactivity.ui.mainActivity.mainScreen

import com.alphan.mainactivity.core.BaseView

interface MainScreenView : BaseView {

    fun initGpsTracker()

    fun moveCameraToMyLocation()

    fun drawRadiusCircle(radius: Int)

    fun removeCircle()
}