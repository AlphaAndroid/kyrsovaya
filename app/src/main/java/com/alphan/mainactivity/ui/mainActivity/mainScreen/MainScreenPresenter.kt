package com.alphan.mainactivity.ui.mainActivity.mainScreen

import android.annotation.SuppressLint
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BasePresenter
import com.alphan.mainactivity.utils.Constants.SET_OF_RIGHTS_TO_GET_MY_LOCATION
import com.alphan.mainactivity.utils.UserPreferences
import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.location.LocationResult
import javax.inject.Inject

@InjectViewState
class MainScreenPresenter : BasePresenter<MainScreenView>() {

    @Inject
    lateinit var userPreferences: UserPreferences

    init {
        BaseApplication.appComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    fun test(locationResult: LocationResult) {
        /*getRetrofitInstance().getNearbyPlaces("${locationResult.lastLocation.latitude}, ${locationResult.lastLocation.longitude}",
                2000.toString(), "restaurant", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                })*/
    }

    fun findMe() {
        if (hasPermission(SET_OF_RIGHTS_TO_GET_MY_LOCATION)) {
            viewState.initGpsTracker()
            viewState.moveCameraToMyLocation()
        }
    }

    fun checkMyLocationSettings() {
        if (hasPermission(SET_OF_RIGHTS_TO_GET_MY_LOCATION)) {
            viewState.initGpsTracker()
            if (userPreferences.shouldShowRadius)
                viewState.drawRadiusCircle(userPreferences.searchRadius)
            else viewState.removeCircle()
        }
    }
}