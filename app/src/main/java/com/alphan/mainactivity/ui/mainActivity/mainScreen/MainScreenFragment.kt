package com.alphan.mainactivity.ui.mainActivity.mainScreen

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BaseFragment
import com.alphan.mainactivity.ui.mainActivity.MainActivity
import com.alphan.mainactivity.utils.GpsService
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_main_screen.*
import javax.inject.Inject


class MainScreenFragment : BaseFragment(), MainScreenView, OnMapReadyCallback, GpsService.LocationUpdateCallback {

    @InjectPresenter
    lateinit var presenter: MainScreenPresenter

    @Inject
    lateinit var appContext: Application

    private var mGoogleMap: GoogleMap? = null
    private val mCircleOptions = CircleOptions()
    private val mGpsService = GpsService()
    private var mRadius = 1000
    private var mCircle: Circle? = null
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
        mGpsService.stop()
        presenter.checkMyLocationSettings()
    }

    override fun onPause() {
        super.onPause()
        mGpsService.stop()
    }

    override fun onLocationResult(locationResult: LocationResult) {
        mCircleOptions.radius(mRadius.toDouble())
        mCircleOptions.center(
            LatLng(
                locationResult.lastLocation.latitude,
                locationResult.lastLocation.longitude
            )
        )
        if (mCircle == null) mCircle = mGoogleMap?.addCircle(mCircleOptions)
        mCircle?.radius = mRadius.toDouble()
    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
        customizeMap()
        presenter.findMe()
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
                        ), 13.0f
                    )
                )
            }
        }
    }

    override fun removeCircle() {
        mGpsService.stop()
        mCircle?.remove()
        mCircle = null
    }

    @SuppressLint("MissingPermission")
    override fun initGpsTracker() {
        mGoogleMap?.run { isMyLocationEnabled = true }
    }

    override fun drawRadiusCircle(radius: Int) {
        mGpsService.start(appContext, this)
        mRadius = radius
        mCircleOptions.strokeColor(ContextCompat.getColor(appContext, R.color.colorPrimary))
        mCircleOptions.fillColor(ContextCompat.getColor(appContext, R.color.colorSemiTransparentBlue))
        mCircleOptions.strokeWidth(4f)
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
