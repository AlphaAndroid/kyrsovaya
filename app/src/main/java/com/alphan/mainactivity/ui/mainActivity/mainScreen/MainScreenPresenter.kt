package com.alphan.mainactivity.ui.mainActivity.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import com.alphan.mainactivity.api.NearbyPlacesResponse
import com.alphan.mainactivity.api.getRetrofitInstance
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BasePresenter
import com.alphan.mainactivity.utils.Constants.*
import com.alphan.mainactivity.utils.UserPreferences
import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MainScreenPresenter : BasePresenter<MainScreenView>() {

    @Inject
    lateinit var userPreferences: UserPreferences
    private var mPlaces = ArrayList<NearbyPlacesResponse.Place>()

    init {
        BaseApplication.appComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    fun findLocations(locationResult: LocationResult) {
        getRetrofitInstance().getNearbyPlaces(
                RU,
                "${locationResult.lastLocation.latitude}, ${locationResult.lastLocation.longitude}",
                userPreferences.searchRadius.toString(),
                userPreferences.selectedPlaceType,
                API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNearbyPlacesReceived(it) }, {})
    }

    fun findMe() {
        if (hasPermission(SET_OF_RIGHTS_TO_GET_MY_LOCATION)) {
            viewState.initGpsTracker()
            viewState.moveCameraToMyLocation()
        }
    }

    fun checkMyLocationSettings() {
        if (hasPermission(SET_OF_RIGHTS_TO_GET_MY_LOCATION)) {
            if (userPreferences.shouldShowRadius)
                viewState.drawRadiusCircle(userPreferences.searchRadius)
            else viewState.removeCircle()
            viewState.initGpsTracker()
        }
    }

    fun onMarkerClicked(marker: Marker) {
        val index = Integer.valueOf(marker.tag.toString())
        val place = mPlaces[index]
        viewState.showBottomSheet(place.name, place.address, place.rating, place.openingStatus?.isOpened)
    }

    private fun onNearbyPlacesReceived(response: NearbyPlacesResponse) {
        val placesLatLng = ArrayList<LatLng>()
        mPlaces.addAll(response.results)
        response.results.forEach {
            placesLatLng.add(
                    LatLng(
                            it.geometry.location.lat,
                            it.geometry.location.lng
                    )
            )
        }
        viewState.setMarkers(placesLatLng)
    }
}