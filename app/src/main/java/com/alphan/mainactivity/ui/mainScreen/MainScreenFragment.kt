package com.alphan.mainactivity.ui.mainScreen

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BaseFragment
import com.alphan.mainactivity.ui.mainActivity.MainActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_main_screen.*
import javax.inject.Inject


class MainScreenFragment : BaseFragment(), MainScreenView, OnMapReadyCallback {

    @InjectPresenter
    lateinit var presenter: MainScreenPresenter

    @Inject
    lateinit var appContext: Application

    private var mGoogleMap: GoogleMap? = null
    private lateinit var mLocationClient: FusedLocationProviderClient

    init {
        BaseApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        presenter.checkMyLocationSettings()
    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
        customizeMap()
    }

    @SuppressLint("MissingPermission")
    override fun moveCameraToMyLocation() {
        mLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val mLocation = task.result
                mGoogleMap?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            mLocation?.latitude ?: 0.0,
                            mLocation?.longitude ?: 0.0
                        ), 12.0f
                    )
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun initGpsTracker() {
        mGoogleMap?.run { isMyLocationEnabled = true }
    }

    fun onFindMeBtnClicked() {
        presenter.findMe()
    }

    private fun customizeMap() {
        mGoogleMap?.run {
            uiSettings.isCompassEnabled = false
            uiSettings.isCompassEnabled = false
            uiSettings.isMapToolbarEnabled = false
            uiSettings.isMyLocationButtonEnabled = false
            uiSettings.isZoomControlsEnabled = false
        }
    }

    private fun init() {
        val mapFragment =
            childFragmentManager.findFragmentById(com.alphan.mainactivity.R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        mLocationClient = LocationServices.getFusedLocationProviderClient(appContext)
        navImg.setOnClickListener { (activity as MainActivity).openDrawer() }
    }
}
