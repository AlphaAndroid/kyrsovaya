package com.alphan.mainactivity.ui.mainActivity

import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BasePresenter
import com.alphan.mainactivity.utils.Constants.SET_OF_RIGHTS_TO_GET_MY_LOCATION
import com.alphan.mainactivity.utils.UserPreferences
import com.arellomobile.mvp.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter : BasePresenter<MainActivityView>() {

    @Inject
    lateinit var userPreferences: UserPreferences

    init {
        BaseApplication.appComponent.inject(this)
    }

    fun initDrawer() {
        viewState.setGeoStatus(hasPermission(SET_OF_RIGHTS_TO_GET_MY_LOCATION))
        viewState.setRadiusSwitcherStatus(userPreferences.shouldShowRadius)
    }
}