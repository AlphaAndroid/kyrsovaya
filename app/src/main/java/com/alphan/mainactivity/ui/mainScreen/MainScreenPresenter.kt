package com.alphan.mainactivity.ui.mainScreen

import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BasePresenter
import com.alphan.mainactivity.utils.Constants.SET_OF_RIGHTS_TO_GET_MY_LOCATION
import com.arellomobile.mvp.InjectViewState

@InjectViewState
class MainScreenPresenter : BasePresenter<MainScreenView>() {

    init {
        BaseApplication.appComponent.inject(this)
    }

    fun findMe() {
        if (hasPermission(SET_OF_RIGHTS_TO_GET_MY_LOCATION)) {
            viewState.initGpsTracker()
            viewState.moveCameraToMyLocation()
        }
    }

    fun checkMyLocationSettings() {
        if (hasPermission(SET_OF_RIGHTS_TO_GET_MY_LOCATION))
            viewState.initGpsTracker()
    }
}