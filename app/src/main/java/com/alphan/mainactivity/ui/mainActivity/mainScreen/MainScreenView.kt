package com.alphan.mainactivity.ui.mainActivity.mainScreen

import com.alphan.mainactivity.core.BaseView
import com.google.android.gms.maps.model.LatLng

interface MainScreenView : BaseView {

    fun initGpsTracker()

    fun moveCameraToMyLocation()

    fun drawRadiusCircle(radius: Int)

    fun removeCircle()

    fun setMarkers(places: ArrayList<LatLng>)

    fun showBottomSheet(title: String, location: String, rating: Double, isOpened: Boolean?)
}