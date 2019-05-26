package com.alphan.mainactivity.ui.mainScreen

import com.alphan.mainactivity.core.BaseView

interface MainScreenView : BaseView {

    fun initGpsTracker()

    fun moveCameraToMyLocation()
}